import pandas as pd
import os


def get_grn_phenotypes(root_directory_path):
    fitness_values = []
    csv_files = []
    for root, dirs, files in os.walk(root_directory_path):
        for a_file in files:
            if a_file.endswith(".pheno"):
                csv_files.append(root + os.sep + a_file)

    for a_file in csv_files:
        a_df = pd.read_csv(a_file, '\t')
        for a_phenotype in a_df['Phenotype'][1:]:
            fitness_values.append(map(int, a_phenotype[1:-1].split(',')))
    # return sorted(fitness_values)
    return fitness_values

path_1 = "/Users/zhenyueqin/Downloads/diploid-grn-2-target-10-matrix/"

print get_grn_phenotypes(path_1)
