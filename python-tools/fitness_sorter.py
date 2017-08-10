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

a_root_directory_path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
                          "-Hotspots/generated-outputs/diploid-grn-3-target-10-matrix-random-spx-7"

a_root_directory_path_2 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
                          "-Hotspots/generated-outputs/hotspot-diploid-grn-3-target-10-matrix-evolved-spx-7"

print get_fitness_values(a_root_directory_path_1, -1).__len__()
print get_fitness_values(a_root_directory_path_2, -1).__len__()

for ix in range(1799, 1800):
    print "generation: ", ix
    a = get_fitness_values(a_root_directory_path_1, ix)
    b = get_fitness_values(a_root_directory_path_2, ix)

    if scipy.stats.wilcoxon(a, b)[1] <= 1:
        print "mean a: ", sum(a) / a.__len__()
        print "mean b: ", sum(b) / b.__len__()

        print scipy.stats.wilcoxon(a, b)
        print scipy.stats.ttest_ind(a, b)

