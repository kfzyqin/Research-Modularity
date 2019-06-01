import os
import pandas as pd
import tools.io_tools as io_tools


def get_entry_values_of_an_experiment(root_directory_path, entry, index=-1):
    fitness_values = []
    csv_files = []
    sub_dirs = io_tools.get_immediate_subdirectories(root_directory_path)
    for a_sub_dir in sub_dirs:
        a_stat_csv = os.path.join(a_sub_dir, 'Statistics.csv')
        csv_files.append(a_stat_csv)

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


