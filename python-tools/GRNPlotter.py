import re
import community
import networkx as nx
import os
import math
import matplotlib.pyplot as plt
from networkx.drawing.nx_agraph import write_dot
import numpy as np
from file_processor import get_immediate_subdirectories, save_a_list_graph


class GRNPlotter:
    def __init__(self):
        self.phenotype_patter = re.compile("(?<=Phenotype: \[)(.*)(?=\])")

    def get_best_partition(self, a_grn):
        return community.best_partition(a_grn.to_undirected())

    def get_modularity_value(self, a_grn):
        modularity_partition = {0: 0, 1: 0, 2: 0, 3: 0, 4: 0, 5: 1, 6: 1, 7: 1, 8: 1, 9: 1}
        return community.modularity(modularity_partition, a_grn.to_undirected())

    def get_grn_phenotypes(self, root_directory_path):
        phenotypes = []
        txt_files = []
        for root, dirs, files in os.walk(root_directory_path):
            for a_file in files:
                if a_file.endswith(".txt"):
                    txt_files.append(root + os.sep + a_file)

        for a_file in txt_files:
            for i, line in enumerate(open(a_file)):
                for match in re.finditer(self.phenotype_patter, line):
                    phenotypes.append(map(int, match.groups()[0].split(',')))
        return phenotypes

    def generate_edge_colors(self, a_grn, a_grn_phenotype):
        grn_side_size = int(math.sqrt(len(a_grn_phenotype)))
        for an_edge in a_grn.edges():
            if a_grn_phenotype[an_edge[0] * grn_side_size + an_edge[1]] == -1:
                a_grn.edge[an_edge[0]][an_edge[1]]['color'] = 'red'
            elif a_grn_phenotype[an_edge[0] * grn_side_size + an_edge[1]] == 1:
                a_grn.edge[an_edge[0]][an_edge[1]]['color'] = 'green'
            else:
                a_grn.edge[an_edge[0]][an_edge[1]]['color'] = 'black'

    def get_activation_or_depression(self, target_idx, a_grn_phenotype):
        grn_side_size = int(math.sqrt(len(a_grn_phenotype)))
        a_sum = 0
        for j in range(grn_side_size):
            a_sum += a_grn_phenotype[j * grn_side_size + target_idx]
        if a_sum > 0:
            return 1
        else:
            return -1

    def generate_node_colors(self, a_grn, a_grn_phenotype):
        grn_side_size = int(math.sqrt(len(a_grn_phenotype)))
        for i in range(grn_side_size):
            if self.get_activation_or_depression(i, a_grn_phenotype) == 1:
                a_grn.node[i]['color'] = 'green'
            else:
                a_grn.node[i]['color'] = 'red'
        for i in a_grn.nodes_with_selfloops():
            a_grn.node[i]['color'] = 'yellow'

    def generate_directed_grn(self, a_grn_phenotype):
        grn = nx.DiGraph()
        grn_side_size = int(math.sqrt(len(a_grn_phenotype)))
        for i in range(grn_side_size):
            grn.add_node(i)

        for i in range(grn_side_size):
            for j in range(grn_side_size):
                if a_grn_phenotype[j * grn_side_size + i] != 0:
                    grn.add_edge(j, i, {'influence': a_grn_phenotype[j * grn_side_size + i]})

                    if a_grn_phenotype[j * grn_side_size + i] == 1:
                        grn[j][i]['color'] = 'green'
                    elif a_grn_phenotype[j * grn_side_size + i] == -1:
                        grn[j][i]['color'] = 'red'
        return grn

    def get_grn_modularity_values(self, root_directory_path):
        modularity_values = []
        phenotypes = self.get_grn_phenotypes(root_directory_path)
        for a_phenotype in phenotypes:
            a_grn = self.generate_directed_grn(a_phenotype)
            modularity_values.append(self.get_modularity_value(a_grn))
            # partition_set = set()
            # for ele in a_partition.values():
            #     partition_set.add(ele)
            # modularity_values.append((len(partition_set), get_modularity_value(a_grn)))
        return modularity_values

    def draw_a_grn(self, grn, is_to_save=True, save_path="", file_name="", with_labels=False):
        # drawing
        # pos = nx.spring_layout(grn)
        if isinstance(grn, list):
            grn = self.generate_directed_grn(grn)
        pos = nx.circular_layout(grn)
        partition = {0: 0, 1: 0, 2: 0, 3: 0, 4: 0, 5: 1, 6: 1, 7: 1, 8: 1, 9: 1}
        # pos = draw_communities.community_layout(grn, partition)
        nx.draw(grn, pos, node_color=partition.values(), with_labels=with_labels,
                edge_color=nx.get_edge_attributes(grn, 'color').values())

        if not is_to_save:
            plt.show()
        if is_to_save:
            if save_path == "" or file_name == "":
                raise Exception('Save path is not specified.')
            plt.savefig(save_path + os.sep + file_name)
            plt.close()

            for key, value in np.ndenumerate(grn):
                grn.node[key[0]]['pos'] = value
            write_dot(grn, save_path + os.sep + 'directed_graph.dot')

    def get_module_values_of_an_experiment(self, a_path, generation, draw_modularity=True, draw_grn=True):
        sub_directories = get_immediate_subdirectories(a_path)
        final_module_value_list = []
        for a_directory in sub_directories:
            phenotypes = self.get_grn_phenotypes(a_directory)
            if len(phenotypes) > 0:
                a_grn = self.generate_directed_grn(phenotypes[generation])
                modularity_values = self.get_grn_modularity_values(a_directory)
                final_module_value_list.append(modularity_values[generation])

                if draw_modularity:
                    save_a_list_graph(modularity_values, 'Modularity', a_directory, 'modularity.png')
                if draw_grn:
                    self.draw_a_grn(a_grn, is_to_save=True, save_path=a_directory, file_name='graph.png',
                                    with_labels=True)
        return final_module_value_list

    def get_module_values_of_a_trial(self, a_directory, draw_modularity=True):
        phenotypes = self.get_grn_phenotypes(a_directory)
        if len(phenotypes) > 0:
            modularity_values = self.get_grn_modularity_values(a_directory)
            if draw_modularity:
                save_a_list_graph(modularity_values, 'Modularity', a_directory, 'modularity.png')
            return modularity_values

