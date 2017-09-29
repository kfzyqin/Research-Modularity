import os
import re
import pandas as pd
import community
import networkx as nx
import math

pattern = re.compile("(?<=Phenotype: \[)(.*)(?=\])")


def get_fitness_values(root_directory_path):
    fitness_values = []
    csv_files = []
    for root, dirs, files in os.walk(root_directory_path):
        for a_file in files:
            if a_file.endswith(".csv"):
                csv_files.append(root + os.sep + a_file)

    if len(csv_files) == 1:
        for a_file in csv_files:
            a_df = pd.read_csv(a_file, '\t')
            for a_generation in range(len(a_df)):
                fitness_values.append(a_df['Best'].iloc[a_generation])
    else:
        raise Exception("The path is not correct. ")
    return fitness_values


def get_grn_phenotypes(root_directory_path):
    phenotypes = []
    txt_files = []
    for root, dirs, files in os.walk(root_directory_path):
        for a_file in files:
            if a_file.endswith(".txt"):
                txt_files.append(root + os.sep + a_file)

    if len(txt_files) == 1:
        for a_file in txt_files:
            for i, line in enumerate(open(a_file)):
                for match in re.finditer(pattern, line):
                    phenotypes.append(map(int, match.groups()[0].split(',')))

    else:
        raise Exception("The path is not correct. ")
    return phenotypes


def get_fitness_modularity_pair_of_a_trial(a_path):
    phenotypes = get_grn_phenotypes(a_path)
    grns = map(generate_directed_grn, phenotypes)
    modularity_values = map(get_modularity_value, grns)
    fitnesses = get_fitness_values(a_path)
    return zip(fitnesses, modularity_values)


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


def get_modularity_value(a_grn):
    modularity_partition = {0: 0, 1: 0, 2: 0, 3: 0, 4: 0, 5: 1, 6: 1, 7: 1, 8: 1, 9: 1, 10: 2, 11: 2, 12: 2, 13: 2, 14: 2}
    return community.modularity(modularity_partition, a_grn.to_undirected())


def get_the_fittest_individual_in_the_most_modular_networks(a_path, starting_generation):
    tuples = get_fitness_modularity_pair_of_a_trial(a_path)

    max_modularity = max(x[1] for x in tuples)

    tmp_fitness = 0
    target_generation = 0
    for a_tuple_idx in range(starting_generation, len(tuples)):
        if tuples[a_tuple_idx][1] == max_modularity:
            if tuples[a_tuple_idx][0] > tmp_fitness:
                tmp_fitness = tuples[a_tuple_idx][0]
                target_generation = a_tuple_idx

    return target_generation, tuples[target_generation][0], tuples[target_generation][1]


def get_the_least_modular_individual_in_the_fittest_networks(a_path, starting_generation):
    tuples = get_fitness_modularity_pair_of_a_trial(a_path)

    max_fitness = max(tuples)[0]

    tmp_modularity = 1
    target_generation = 0
    for a_tuple_idx in range(starting_generation, len(tuples)):
        if tuples[a_tuple_idx][0] == max_fitness:
            if tuples[a_tuple_idx][1] < tmp_modularity:
                tmp_modularity = tuples[a_tuple_idx][1]
                target_generation = a_tuple_idx
    return target_generation, tuples[target_generation][0], tuples[target_generation][1]


file_path = "/Users/Chinyuer/Software-Engineering/COMP4560/Chin-GA-Project/thesis-data/" \
            "improved-crossover-for-modularity/chin-crossover/2017-08-12-11-24-22/"

print get_the_fittest_individual_in_the_most_modular_networks(file_path, 501)
print get_the_least_modular_individual_in_the_fittest_networks(file_path, 501)
