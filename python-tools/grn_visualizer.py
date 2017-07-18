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
    return community.modularity(community.best_partition(a_grn.to_undirected()), a_grn.to_undirected())


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
    # return sorted(phenotypes)
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
                grn.add_edge(i, j)
    return grn


def get_grn_modularity_values(root_directory_path):
    modularity_values = []
    phenotypes = get_grn_phenotypes(root_directory_path)
    for a_phenotype in phenotypes:
        a_grn = generate_directed_grn(a_phenotype)
        a_partition = get_best_partition(a_grn)
        modularity_values.append(get_modularity_value(a_grn))
    return modularity_values


def draw_a_grn(grn, is_to_save=True, save_path="", file_name=""):
    # drawing
    pos = nx.spring_layout(grn)
    partition = community.community_louvain.best_partition(grn.to_undirected())
    # pos = draw_communities.community_layout(grn, partition)
    nx.draw(grn, pos, node_color=partition.values())

    if not is_to_save:
        plt.show()
    if is_to_save:
        if save_path== "" or file_name == "":
            raise Exception('Save path is not specified.')
        plt.savefig(save_path + os.sep + file_name)
        plt.close()

        # for key, value in np.ndenumerate(grn):
        #     grn.node[key[0]]['pos'] = value
        # write_dot(grn, str(grn.__hash__()) + 'directed_graph.dot')
        # os.system('dot -T png ' + str(grn.__hash__()) + 'directed_graph.dot' + '>' +
        #           save_path + os.sep + 'directed_graph.png')
        # os.remove(str(grn.__hash__()) + 'directed_graph.dot')

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


def get_modularity_value_maxes(path_1):
    sub_directories = get_immediate_subdirectories(path_1)
    modularity_value_maxes = []

    for a_directory in sub_directories:

        modularity_values = get_grn_modularity_values(a_directory)
        modularity_value_maxes.append(max(modularity_values))

    return modularity_value_maxes


path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
                          "-Hotspots/generated-outputs/data-2017-07-15/" \
                          "larson-want-to-see-20-edges-in-324-nodes/haploid-grn-matrix-2-target-10"

path_2 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
                          "-Hotspots/generated-outputs/data-2017-07-15/" \
                          "larson-want-to-see-20-edges-in-324-nodes/hotspot-haploid-grn-matrix-2-target-10"

# path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
#                           "-Hotspots/generated-outputs/haploid-grn-2-target-10-matrix-larson-no-crossover/"


sub_directories = get_immediate_subdirectories(path_2)

for a_directory in sub_directories:
    phenotypes = get_grn_phenotypes(a_directory)
    a_grn = generate_directed_grn(phenotypes[-1])

    modularity_values = get_grn_modularity_values(a_directory)
    save_a_list_graph(modularity_values, a_directory, 'modularity.png')

    draw_a_grn(a_grn, is_to_save=True, save_path=a_directory, file_name='graph.png')

# a = get_modularity_value_maxes(path_1)
# b = get_modularity_value_maxes(path_2)
#
# print "mean a: ", sum(a) / a.__len__()
# print "mean b: ", sum(b) / b.__len__()
#
# print scipy.stats.wilcoxon(a, b)
# print scipy.stats.ttest_ind(a, b)

