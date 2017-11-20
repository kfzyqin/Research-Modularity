import pandas as pd
import os
import scipy
import scipy.stats

def get_fitness_values(root_directory_path, index):
    fitness_values = []
    csv_files = []
    for root, dirs, files in os.walk(root_directory_path):
        for a_file in files:
            if a_file.endswith(".csv"):
                csv_files.append(root + os.sep + a_file)

    for a_file in csv_files:
        a_df = pd.read_csv(a_file, '\t')
        fitness_values.append(a_df['Best'].iloc[index])
    # return sorted(fitness_values)
    return fitness_values


path_1 = "/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/" \
            "half-probability-mutation"

path_2 = "/Users/qin/Software-Engineering/Chin-GA-Project/" \
                            "thesis-data/larson-experiments-repeats/no-hotspots"

# a_root_directory_path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic-Hotspots/" \
#                             "generated-outputs/data-2017-08-12/" \
#                           "15-target-3-module-work/haploid-grn-matrix-2-target-10-12"
#
#
# a_root_directory_path_2 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic-Hotspots/" \
#                             "generated-outputs/data-2017-08-12/" \
#                           "15-target-3-module-work/hotspot-haploid-grn-matrix-2-target-10-12"

# a = get_fitness_values(path_1, 1999)
# b = get_fitness_values(path_1, 1999)
#
# if scipy.stats.wilcoxon(a, b)[1] <= 0.05:
#     print "mean a: ", sum(a) / a.__len__()
#     print "mean b: ", sum(b) / b.__len__()
#     print "p-value by wilcoxon: ", scipy.stats.wilcoxon(a, b)
#     print "p-value by t test: ", scipy.stats.ttest_ind(a, b)

print get_fitness_values(path_1, 1999).__len__()
# print get_fitness_values(path_2, 1999).__len__()

# a_generation = -1
# while a_generation <= -1:
#     print "generation: ", a_generation
#     a = get_fitness_values(path_1, -2)
#     b = get_fitness_values(path_2, -2)
#
#     if scipy.stats.wilcoxon(a, b)[1] <= 1:
#         print "mean a: ", sum(a) / a.__len__()
#         print "mean b: ", sum(b) / b.__len__()
#
#         print "p-value by wilcoxon: ", scipy.stats.wilcoxon(a, b)
#         print "p-value by t test: ", scipy.stats.ttest_ind(a, b)
#     a_generation += 1

