import networkx as nx
import math
import matplotlib.pyplot as plt
from networkx.drawing.nx_agraph import write_dot
import os
import community
from networkx.algorithms.community import *
import quality
import pandas as pd
import re
import numpy as np
import draw_communities
import scipy
import scipy.stats

pattern = re.compile("(?<=Phenotype: \[)(.*)(?=\])")


def get_immediate_subdirectories(a_dir):
    return [(a_dir + os.sep + name) for name in os.listdir(a_dir)
        if os.path.isdir(os.path.join(a_dir, name))]


def get_best_partition(a_grn):
    return community.best_partition(a_grn.to_undirected())


def get_modularity_value(a_grn):
    # dendrogram = community.generate_dendrogram(a_grn.to_undirected())
    # print dendrogram
    modularity_partition = {0: 0, 1: 0, 2: 0, 3: 0, 4: 0, 5: 1, 6: 1, 7: 1, 8: 1, 9: 1, 10: 2, 11: 2, 12: 2, 13: 2, 14: 2}
    return community.modularity(modularity_partition, a_grn.to_undirected())


def get_grn_phenotypes(root_directory_path):
    phenotypes = []
    txt_files = []
    for root, dirs, files in os.walk(root_directory_path):
        for a_file in files:
            if a_file.endswith(".txt"):
                txt_files.append(root + os.sep + a_file)

    for a_file in txt_files:
        for i, line in enumerate(open(a_file)):
            for match in re.finditer(pattern, line):
                phenotypes.append(map(int, match.groups()[0].split(',')))
    return phenotypes


def generate_edge_colors(a_grn, a_grn_phenotype):
    grn_side_size = int(math.sqrt(len(a_grn_phenotype)))
    for an_edge in a_grn.edges():
        if a_grn_phenotype[an_edge[0] * grn_side_size + an_edge[1]] == -1:
            a_grn.edge[an_edge[0]][an_edge[1]]['color'] = 'red'
        elif a_grn_phenotype[an_edge[0] * grn_side_size + an_edge[1]] == 1:
            a_grn.edge[an_edge[0]][an_edge[1]]['color'] = 'green'
        else:
            a_grn.edge[an_edge[0]][an_edge[1]]['color'] = 'black'


def get_activation_or_depression(target_idx, a_grn_phenotype):
    grn_side_size = int(math.sqrt(len(a_grn_phenotype)))
    a_sum = 0
    for j in range(grn_side_size):
        a_sum += a_grn_phenotype[j * grn_side_size + target_idx]
    if a_sum > 0:
        return 1
    else:
        return -1


def generate_node_colors(a_grn, a_grn_phenotype):
    grn_side_size = int(math.sqrt(len(a_grn_phenotype)))
    for i in range(grn_side_size):
        if get_activation_or_depression(i, a_grn_phenotype) == 1:
            a_grn.node[i]['color'] = 'green'
        else:
            a_grn.node[i]['color'] = 'red'
    for i in a_grn.nodes_with_selfloops():
        a_grn.node[i]['color'] = 'yellow'


def generate_directed_grn(a_grn_phenotype):
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


def get_grn_modularity_values(root_directory_path):
    modularity_values = []
    phenotypes = get_grn_phenotypes(root_directory_path)
    for a_phenotype in phenotypes:
        a_grn = generate_directed_grn(a_phenotype)
        modularity_values.append(get_modularity_value(a_xgrn))
        # partition_set = set()
        # for ele in a_partition.values():
        #     partition_set.add(ele)
        # modularity_values.append((len(partition_set), get_modularity_value(a_grn)))
    return modularity_values


def draw_a_grn(grn, is_to_save=True, save_path="", file_name="", with_labels=False):
    # drawing
    # pos = nx.spring_layout(grn)
    pos = nx.circular_layout(grn)
    partition = {0: 0, 1: 0, 2: 0, 3: 0, 4: 0, 5: 1, 6: 1, 7: 1, 8: 1, 9: 1, 10: 2, 11: 2, 12: 2, 13: 2, 14: 2}
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
        # os.system('dot -T png ' + str(grn.__hash__()) + 'directed_graph.dot' + '>' +
        #           save_path + os.sep + 'directed_graph.png')
        # os.remove(save_path + os.sep + 'directed_graph.dot')


def plot_a_list(a_list):
    plt.plot(a_list)
    plt.ylabel('Modularity')
    plt.show()


def save_a_list_graph(a_list, path, file_name):
    plt.plot(a_list)
    plt.ylabel('Modularity')
    plt.savefig(path + os.sep + file_name)
    # plt.plot()
    plt.close()


def get_modularity_value_maxes(a_path, starting_generation):
    sub_directories = get_immediate_subdirectories(a_path)
    modularity_value_maxes = []

    for a_directory in sub_directories:

        modularity_values = get_grn_modularity_values(a_directory)[starting_generation:]
        modularity_value_maxes.append(max(modularity_values))

    return modularity_value_maxes


# path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic-Hotspots/" \
#                             "thesis-data/improved-crossover-for-modularity/larson-crossover"

path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic-Hotspots/" \
                            "thesis-data/elite-reduce-modularity-previous/"

path_2 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic-Hotspots/" \
                            "thesis-data/elite-reduce-modularity-previous/"


def get_module_values(a_path, generation, draw_modularity = True, draw_grn = True):
    sub_directories = get_immediate_subdirectories(a_path)
    final_module_value_list = []
    for a_directory in sub_directories:
        phenotypes = get_grn_phenotypes(a_directory)
        if (len(phenotypes) > 0):
            a_grn = generate_directed_grn(phenotypes[generation])
            modularity_values = get_grn_modularity_values(a_directory)
            final_module_value_list.append(modularity_values[generation])

            if draw_modularity:
                save_a_list_graph(modularity_values, a_directory, 'modularity.png')
            if draw_grn:
                draw_a_grn(a_grn, is_to_save=True, save_path=a_directory, file_name='graph.png', with_labels=True)
    return final_module_value_list


def get_module_values_of_a_trial(a_directory, generation):
    phenotypes = get_grn_phenotypes(a_directory)
    if (len(phenotypes) > 0):
        a_grn = generate_directed_grn(phenotypes[generation])
        modularity_values = get_grn_modularity_values(a_directory)
        # break
        save_a_list_graph(modularity_values, a_directory, 'modularity.png')
        draw_a_grn(a_grn, is_to_save=True, save_path=a_directory, file_name='graph.png', with_labels=True)
        return modularity_values

c_1 = get_module_values(path_1, 299, draw_grn=True, draw_modularity=True)
c_2 = get_module_values(path_2, -1, draw_grn=True, draw_modularity=True)

print "mean c_1: ", sum(c_1) / c_1.__len__()
print "mean c_2: ", sum(c_2) / c_2.__len__()

print scipy.stats.wilcoxon(c_1, c_2)
print scipy.stats.ttest_ind(c_1, c_2)

#
# print "mean a: ", sum(a) / a.__len__()
# print "mean b: ", sum(b) / b.__len__()
#
# print scipy.stats.wilcoxon(a, b)
# print scipy.stats.ttest_ind(a, b)


# a = get_modularity_value_maxes(path_1, starting_generation=301)
# b = get_modularity_value_maxes(path_2, starting_generation=301)
#
# print "mean a: ", sum(a) / a.__len__()
# print "mean b: ", sum(b) / b.__len__()
#
# print scipy.stats.wilcoxon(a, b)
# print scipy.stats.ttest_ind(a, b)

print c_1
