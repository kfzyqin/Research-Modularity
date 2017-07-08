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
        fitness_values.append(a_df['Best'].iloc[-1])
    # return sorted(fitness_values)
    return fitness_values

a_root_directory_path_1 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
                          "-Hotspots/generated-outputs/haploid-grn-2-target-10-matrix-larson/"

a_root_directory_path_2 = "/Users/zhenyueqin/Software-Engineering/COMP4560-Advanced-Computing-Project/Genetic" \
                          "-Hotspots/generated-outputs/haploid-grn-2-target-10-matrix-larson-no-crossover/"

print get_fitness_values(a_root_directory_path_1).__len__()
print get_fitness_values(a_root_directory_path_2).__len__()

a = get_fitness_values(a_root_directory_path_1)
b = get_fitness_values(a_root_directory_path_2)[:35]

print scipy.stats.wilcoxon(a, b)



