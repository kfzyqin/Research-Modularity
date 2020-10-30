import re
import community
import networkx as nx
import os
import math
import matplotlib.pyplot as plt
from networkx.drawing.nx_agraph import write_dot
import numpy as np
from file_processor import get_immediate_subdirectories, save_a_list_graph
import file_processor as fp
import tools.io_tools as io_tools


class GRNPlotter:
    def __init__(self):
        self.phenotype_patter = re.compile("(?<=Phenotype: \[)(.*)(?=\])")

    def get_best_partition(self, a_grn):
        return community.best_partition(a_grn.to_undirected())

    def get_non_self_edge_modularity_value(self, a_grn, louvain=False):
        if isinstance(a_grn, list):
            a_grn = self.generate_directed_grn(a_grn)
        if louvain:
            modularity_partition = community.best_partition(a_grn.to_undirected())
        else:
            node_no = len(a_grn.nodes())
            modularity_partition = {}
            for i in range(node_no):
                modularity_partition[i] = int(i / 5)
        return community.modularity(modularity_partition, a_grn.to_undirected())


    def get_modularity_value(self, a_grn, louvain=False):
        if isinstance(a_grn, list):
            a_grn = self.generate_directed_grn(a_grn)
        if louvain:
            modularity_partition = community.best_partition(a_grn.to_undirected())
        else:
            node_no = len(a_grn.nodes())
            modularity_partition = {}
            for i in range(node_no):
                modularity_partition[i] = int(i / 5)
        return community.modularity(modularity_partition, a_grn.to_undirected())

    def get_grn_phenotypes(self, root_directory_path):
        phenotypes = []
        sum_location = os.path.join(root_directory_path, 'Summary.txt')

        exists = os.path.isfile(sum_location)
        if exists:
            for i, line in enumerate(open(sum_location)):
                for match in re.finditer(self.phenotype_patter, line):
                    phenotypes.append(map(int, match.groups()[0].split(',')))
        return phenotypes

    def get_exp_fittest_grn_edge_num(self, root_path, index=-1, sample_size=100):
        trial_dirs = fp.get_immediate_subdirectories(root_path)
        final_phes = []
        for a_trail_dir in trial_dirs:
            phenotypes = self.get_grn_phenotypes(a_trail_dir)
            if phenotypes:
                phenotypes = phenotypes[index]
                final_phes.append(phenotypes)
            if len(final_phes) >= sample_size:
                break
        return list([io_tools.count_number_of_edges(a_phe) for a_phe in final_phes])

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
                    grn.add_edge(j, i)

                    if a_grn_phenotype[j * grn_side_size + i] == 1:
                        grn[j][i]['color'] = 'green'
                    elif a_grn_phenotype[j * grn_side_size + i] == -1:
                        grn[j][i]['color'] = 'red'
        return grn

    def get_grn_modularity_values(self, root_directory_path, louvain=False):
        modularity_values = []
        phenotypes = self.get_grn_phenotypes(root_directory_path)
        for a_phenotype in phenotypes:
            a_grn = self.generate_directed_grn(a_phenotype)
            modularity_values.append(self.get_modularity_value(a_grn, louvain))
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

        node_no = len(grn.nodes())
        partition = {}
        for i in range(node_no):
            partition[i] = int(i / 5)

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

    def get_avg_module_values_for_each_generation_of_an_experiment(self, a_path, start_gen=0, end_gen=None,
                                                                   louvain=False, no_self_edge=False, avg_gen=True):
        sub_directories = get_immediate_subdirectories(a_path)
        rtn_modularity_list = []

        for a_directory in sub_directories:
            phenotypes = self.get_grn_phenotypes(a_directory)
            if len(phenotypes) > 0:
                if end_gen is None:
                    end_gen = len(phenotypes)
                for a_gen in range(start_gen, end_gen):
                    target_phenotype = phenotypes[a_gen]
                    if no_self_edge:
                        side_grn_size = int(math.sqrt(len(target_phenotype)))
                        for i in range(side_grn_size):
                            target_phenotype[i * side_grn_size + i] = 0
                    a_grn = self.generate_directed_grn(target_phenotype)
                    if len(rtn_modularity_list) <= a_gen:
                        rtn_modularity_list.append([self.get_modularity_value(a_grn, louvain)])
                    else:
                        rtn_modularity_list[a_gen].append(self.get_modularity_value(a_grn, louvain))

        if avg_gen:
            for i in range(len(rtn_modularity_list)):
                rtn_modularity_list[i] = sum(rtn_modularity_list[i]) / len(rtn_modularity_list[i])
        return rtn_modularity_list


    def get_module_values_of_an_experiment(self, a_path, generation=-1, draw_modularity=False, draw_grn=False,
                                           draw_gen_avg_modularity=False, louvain=False, no_self_edge=False):
        sub_directories = get_immediate_subdirectories(a_path)
        all_modularities = []
        final_module_value_list = []
        for a_directory in sub_directories:
            phenotypes = self.get_grn_phenotypes(a_directory)
            if len(phenotypes) > 0:
                target_phenotype = phenotypes[generation]
                if no_self_edge:
                    side_grn_size = int(math.sqrt(len(target_phenotype)))
                    for i in range(side_grn_size):
                        target_phenotype[i*side_grn_size + i] = 0
                a_grn = self.generate_directed_grn(target_phenotype)
                final_module_value_list.append(self.get_modularity_value(a_grn, louvain))

                if draw_modularity:
                    modularity_values = self.get_grn_modularity_values(a_directory, louvain)
                    all_modularities.append(modularity_values)
                    save_a_list_graph(modularity_values, 'Modularity', a_directory, 'modularity.png')
                    sum_of_modularity = map(sum, zip(*all_modularities))
                    avg_of_modularity = [x / len(all_modularities) for x in sum_of_modularity]
                    if draw_gen_avg_modularity:
                        save_a_list_graph(avg_of_modularity, 'Average Modularity', a_path, 'average_modularity.png')

                if draw_grn:
                    self.draw_a_grn(a_grn, is_to_save=True, save_path=a_directory, file_name='graph.png',
                                    with_labels=True)

        return final_module_value_list

    def get_module_values_of_a_trial(self, a_directory, draw_modularity=True, louvain=False, specialization=None):
        phenotypes = self.get_grn_phenotypes(a_directory)
        if len(phenotypes) > 0:
            modularity_values = self.get_grn_modularity_values(a_directory, louvain)
            if draw_modularity:
                save_a_list_graph(modularity_values, 'Modularity', a_directory, 'modularity.png', specialization)

            return modularity_values


if __name__ == '__main__':
    omega = GRNPlotter()
    # omega.draw_a_grn([0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, -1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, -1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, -1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 0, 0, -1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1],
    #                 is_to_save=False)
    omega.draw_a_grn([0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 0, -1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, -1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, -1, 0, -1, 1, -1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, -1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 1, 0, -1, 0, 0, 1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1, -1, 0]
                     ,is_to_save=False)
    
    # omega.draw_a_grn([0, 0, 1, 0, 1, -1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, -1, 1, -1, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, -1, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, -1, 0, -1, 0, 0, 1, -1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, -1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, -1, 0, 0, 1, 1, 0, 0, -1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 1, 0, 0, 1, 0, 0, 0, 0, 0, -1, 0, -1, -1, 0, 0, 1, 0, 0, -1, 0, -1, 0, 0, 0, 1, 0, -1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 1, 0, 0, 0, -1, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, -1, 1, -1, 0, 0, -1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0]
    #                  ,is_to_save=False)




