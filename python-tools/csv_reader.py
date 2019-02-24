import os
import pandas as pd


def get_entry_values_of_an_experiment(root_directory_path, entry, index=-1):
    fitness_values = []
    csv_files = []
    for root, dirs, files in os.walk(root_directory_path):
        for a_file in files:
            if a_file.endswith(".csv") and not a_file.endswith('volcanoe.csv'):
                # print("a file fitness: ", root)
                csv_files.append(root + os.sep + a_file)

    for a_file in csv_files:
        a_df = pd.read_csv(a_file, '\t')
        # print('headers: ', a_df.columns.values)
        fitness_values.append(a_df[entry].iloc[index])
    return fitness_values


def get_best_entry_values_of_an_exeriment(root_directory_path, entry, start_generation=501):
    fitness_values = []
    csv_files = []
    for root, dirs, files in os.walk(root_directory_path):
        for a_file in files:
            if a_file.endswith(".csv"):
                csv_files.append(root + os.sep + a_file)

    for a_file in csv_files:
        a_df = pd.read_csv(a_file, '\t')
        fitness_values.append(a_df[entry][start_generation:].max())
    return fitness_values


def get_entry_values_of_an_trial(path, entry):
    csv_files = []
    for root, dirs, files in os.walk(path):
        for a_file in files:
            if a_file.endswith(".csv"):
                csv_files.append(root + os.sep + a_file)

    if len(csv_files) == 1:
        a_df = pd.read_csv(csv_files[0], '\t')
        return a_df[entry].tolist()
    else:
        raise Exception("Error: No or multiple CSV files detected. ")


