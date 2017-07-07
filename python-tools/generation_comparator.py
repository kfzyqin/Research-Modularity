import pandas as pd
import os


def get_generation_thresholds(root_directory_path, fitness_threshold, start_generation):
    generations = []
    csv_files = []
    for root, dirs, files in os.walk(root_directory_path):
        for a_file in files:
            if a_file.endswith(".csv"):
                csv_files.append(root + os.sep + a_file)

    for a_file in csv_files:
        a_df = pd.read_csv(a_file, '\t')
        current_generation = 0
        for a_best_fitness in a_df['Best']:
            if a_best_fitness >= fitness_threshold and current_generation > start_generation:
                generations.append(current_generation)
                break
            current_generation += 1

    return generations

a_root_directory_path_1 = "/Users/zhenyueqin/Downloads/diploid-grn-2-target-10-matrix/"
a_root_directory_path_2 = "/Users/zhenyueqin/Downloads/hotspot-diploid-grn-2-target-10-matrix/"

print get_generation_thresholds(a_root_directory_path_1, 0.9, 300)
print get_generation_thresholds(a_root_directory_path_2, 0.9, 300)
