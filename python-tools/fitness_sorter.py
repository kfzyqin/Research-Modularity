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

a_root_directory_path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic-Hotspots/" \
                            "generated-outputs/2017-08-15/" \
                          "diploid-grn-3-target-10-matrix-random-spx-21"


a_root_directory_path_2 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic-Hotspots/" \
                            "generated-outputs/2017-08-15/" \
                          "hotspot-diploid-grn-3-target-10-matrix-evolved-spx-21"

# a_root_directory_path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic-Hotspots/" \
#                             "generated-outputs/data-2017-08-12/" \
#                           "15-target-3-module-work/haploid-grn-matrix-2-target-10-12"
#
#
# a_root_directory_path_2 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic-Hotspots/" \
#                             "generated-outputs/data-2017-08-12/" \
#                           "15-target-3-module-work/hotspot-haploid-grn-matrix-2-target-10-12"

print get_fitness_values(a_root_directory_path_1, -1).__len__()
print get_fitness_values(a_root_directory_path_2, -1).__len__()

a_generation = 3500
while a_generation <= 4000:
    print "generation: ", a_generation
    a = get_fitness_values(a_root_directory_path_1, a_generation)[:30]
    b = get_fitness_values(a_root_directory_path_2, a_generation)[:30]

    if scipy.stats.wilcoxon(a, b)[1] <= 0.05:
        print "mean a: ", sum(a) / a.__len__()
        print "mean b: ", sum(b) / b.__len__()

        print "p-value by wilcoxon: ", scipy.stats.wilcoxon(a, b)
        print "p-value by t test: ", scipy.stats.ttest_ind(a, b)
    a_generation += 1

