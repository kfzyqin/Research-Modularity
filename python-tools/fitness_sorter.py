import pandas as pd
import os
import scipy
import scipy.stats

def get_fitness_values(root_directory_path):
    fitness_values = []
    csv_files = []
    for root, dirs, files in os.walk(root_directory_path):
        for a_file in files:
            if a_file.endswith(".csv"):
                csv_files.append(root + os.sep + a_file)

    for a_file in csv_files:
        a_df = pd.read_csv(a_file, '\t')
        fitness_values.append(a_df['Best'].iloc[1000])
    # return sorted(fitness_values)
    return fitness_values

a_root_directory_path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
                          "-Hotspots/generated-outputs/data-2017-07-11/" \
                          "haploid-grn-2-target-10-matrix-larson-horizontal/"

a_root_directory_path_2 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
                          "-Hotspots/generated-outputs/data-2017-07-11/" \
                          "haploid-grn-2-target-10-matrix-larson-fixed-point/"

print get_fitness_values(a_root_directory_path_1).__len__()
print get_fitness_values(a_root_directory_path_2).__len__()

a = get_fitness_values(a_root_directory_path_1)[:50]
b = get_fitness_values(a_root_directory_path_2)

print "mean a: ", sum(a) / a.__len__()
print "mean b: ", sum(b) / b.__len__()

print scipy.stats.wilcoxon(a, b)
print scipy.stats.ttest_ind(a, b)

