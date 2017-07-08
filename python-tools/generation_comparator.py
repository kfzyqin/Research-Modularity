import pandas as pd
import os
import collections
import scipy
import scipy.stats


def get_generation_thresholds(root_directory_path, fitness_threshold, start_generation):
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
            if a_best_fitness >= fitness_threshold and current_generation > start_generation:
                generations[a_file.replace(root_directory_path, '')] = current_generation -2
                found = True
                break
            current_generation += 1
        if not found:
            generations[a_file.replace(root_directory_path, '')] = current_generation -2

    od = collections.OrderedDict(sorted(generations.items()))
    return od

a_root_directory_path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
                          "-Hotspots/generated-outputs/diploid-grn-2-target-10-matrix/"
a_root_directory_path_2 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
                          "-Hotspots/generated-outputs/hotspot-diploid-grn-2-target-10-matrix/"

print get_generation_thresholds(a_root_directory_path_1, 0.8, 300).__len__()
print get_generation_thresholds(a_root_directory_path_2, 0.8, 300).__len__()

a = get_generation_thresholds(a_root_directory_path_1, 0.8, 300).values()
b = get_generation_thresholds(a_root_directory_path_2, 0.8, 300).values()

print scipy.stats.wilcoxon(a, b)