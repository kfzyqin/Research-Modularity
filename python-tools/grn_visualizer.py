import networkx as nx
import math
import matplotlib.pyplot as plt
from networkx.drawing.nx_agraph import write_dot
import os
import community
from networkx.algorithms.community import *
import quality
import pandas as pd


def get_best_partition(a_grn):
    return community.best_partition(a_grn.to_undirected())


def get_modularity_value(a_grn):
    return community.modularity(get_best_partition(a_grn), a_grn.to_undirected())


def get_grn_phenotypes(root_directory_path):
    phenotypes = []
    csv_files = []
    for root, dirs, files in os.walk(root_directory_path):
        for a_file in files:
            if a_file.endswith(".pheno"):
                csv_files.append(root + os.sep + a_file)

    for a_file in csv_files:
        a_df = pd.read_csv(a_file, '\t')
        for a_phenotype in a_df['Phenotype'][1:]:
            phenotypes.append(map(int, a_phenotype[1:-1].split(',')))
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


def draw_a_grn(grn, partition, grn_phenotype):
    # drawing
    size = float(len(set(partition.values())))
    pos = nx.spring_layout(grn)
    count = 0.
    for com in set(partition.values()):
        count = count + 1.
        list_nodes = [nodes for nodes in partition.keys()
                      if partition[nodes] == com]
        generate_node_colors(grn, grn_phenotype)
        nx.draw_networkx_nodes(grn, pos, list_nodes, node_size=100,
                               node_color=nx.get_node_attributes(grn, 'color').values())

    generate_edge_colors(grn, grn_phenotype)
    nx.draw_networkx_edges(grn, pos, alpha=0.5, edge_color=nx.get_edge_attributes(grn, 'color').values())
    plt.show()


def plot_a_list(a_list):
    plt.plot(a_list)
    plt.ylabel('Modularity')
    plt.show()


path_1 = "/Users/zhenyueqin/Downloads/diploid-grn-2-target-10-matrix/"

phenotypes = get_grn_phenotypes(path_1)
a_grn = generate_directed_grn(phenotypes[-1])
modularity_values = get_grn_modularity_values(path_1)
plot_a_list(modularity_values)
draw_a_grn(a_grn, get_best_partition(a_grn), phenotypes[-1])

write_dot(a_grn,'graph.dot')

os.system('dot -T png graph.dot > graph.png')


