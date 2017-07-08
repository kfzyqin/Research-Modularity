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

pattern = re.compile("(?<=Phenotype: \[)(.*)(?=\])")


def get_immediate_subdirectories(a_dir):
    return [(a_dir + os.sep + name) for name in os.listdir(a_dir)
        if os.path.isdir(os.path.join(a_dir, name))]


def get_best_partition(a_grn):
    return community.best_partition(a_grn.to_undirected())


def get_modularity_value(a_grn):
    return community.modularity(get_best_partition(a_grn), a_grn.to_undirected())


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


def draw_a_grn(grn, partition, grn_phenotype, is_to_save, save_path="", file_name=""):
    # drawing
    size = float(len(set(partition.values())))
    pos = nx.spring_layout(grn)
    count = 0.
    for com in set(partition.values()):
        count = count + 1.
        list_nodes = [nodes for nodes in partition.keys()
                      if partition[nodes] == com]
        # generate_node_colors(grn, grn_phenotype)
        nx.draw_networkx_nodes(grn, pos, list_nodes, node_size=100,
                               # node_color=nx.get_node_attributes(grn, 'color').values())
                               node_color=str(count / size))

    generate_edge_colors(grn, grn_phenotype)
    nx.draw_networkx_edges(grn, pos, alpha=0.5, edge_color=nx.get_edge_attributes(grn, 'color').values())
    if not is_to_save:
        plt.show()
    if is_to_save:
        if save_path== "" or file_name == "":
            raise Exception('Save path is not specified.')
        plt.savefig(save_path + os.sep + file_name)
        plt.close()

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

path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
                          "-Hotspots/generated-outputs/haploid-grn-2-target-10-matrix-larson/"

# path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
#                           "-Hotspots/generated-outputs/haploid-grn-2-target-10-matrix-larson-no-crossover/"

sub_directories = get_immediate_subdirectories(path_1)

for a_directory in sub_directories:
    phenotypes = get_grn_phenotypes(a_directory)
    a_grn = generate_directed_grn(phenotypes[0])

    modularity_values = get_grn_modularity_values(a_directory)
    save_a_list_graph(modularity_values, a_directory, 'modularity.png')

    draw_a_grn(a_grn, get_best_partition(a_grn), phenotypes[-1], True, a_directory, 'graph.png')


# write_dot(a_grn,'graph.dot')
# os.system('dot -T png graph.dot > graph.png')


