import pandas as pd
import os
import collections
import scipy
import scipy.stats


def get_generation_thresholds(root_directory_path, fitness_threshold, start_generation, end_generation=10000):
    generations = dict()
    csv_files = []
    for root, dirs, files in os.walk(root_directory_path):
        for a_file in files:
            if a_file.endswith(".csv"):
                csv_files.append(root + os.sep + a_file)

    for a_file in csv_files:
        a_df = pd.read_csv(a_file, '\t')
        current_generation = 0
        found = False
        for a_best_fitness in a_df['Best']:
            if a_best_fitness >= fitness_threshold and current_generation > start_generation and current_generation < end_generation:
                generations[a_file.replace(root_directory_path, '')] = current_generation -2
                found = True
                break
            current_generation += 1
        if not found:
            generations[a_file.replace(root_directory_path, '')] = current_generation -2

    od = collections.OrderedDict(sorted(generations.items()))
    return od

a_root_directory_path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
                          "-Hotspots/generated-outputs/data-2017-07-09/diploid-grn-3-target-10-random-spx/"

a_root_directory_path_2 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
                          "-Hotspots/generated-outputs/data-2017-07-09/diploid-grn-3-target-10-matrix-no-x/"

print "data one size: ", get_generation_thresholds(a_root_directory_path_1, 0.75, 300).__len__()
print "data two size: ", get_generation_thresholds(a_root_directory_path_2, 0.75, 300).__len__()

a = get_generation_thresholds(a_root_directory_path_1, 0.76, 300, 1050).values()[:50]
b = get_generation_thresholds(a_root_directory_path_2, 0.76, 300, 1050).values()[:50]

print "sample one size: ", a.__len__()
print "sample two size: ", b.__len__()

print "mean 1: ", (sum(a) / a.__len__())
print "mean 2: ", (sum(b) / b.__len__())

print "wilcoxon test: ", scipy.stats.wilcoxon(a, b)
# print "t test: ", scipy.stats.ttest_ind(a, b)

