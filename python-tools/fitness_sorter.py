import pandas as pd
import os


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

a_root_directory_path_1 = "/Users/zhenyueqin/Downloads/diploid-grn-2-target-10-matrix/"
a_root_directory_path_2 = "/Users/zhenyueqin/Downloads/hotspot-diploid-grn-2-target-10-matrix/"

print get_fitness_values(a_root_directory_path_1)
print get_fitness_values(a_root_directory_path_2)



