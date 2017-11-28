import pandas as pd
import os

class FitnessPlotter:
    def __init__(self):
        pass

    def get_fitness_values_of_an_experiment(self, root_directory_path, index=-1):
        fitness_values = []
        csv_files = []
        for root, dirs, files in os.walk(root_directory_path):
            for a_file in files:
                if a_file.endswith(".csv"):
                    csv_files.append(root + os.sep + a_file)

        for a_file in csv_files:
            a_df = pd.read_csv(a_file, '\t')
            fitness_values.append(a_df['Best'].iloc[index])
        return fitness_values

    def get_best_fitness_values_of_an_exeriment(self,  root_directory_path, start_generation=501):
        fitness_values = []
        csv_files = []
        for root, dirs, files in os.walk(root_directory_path):
            for a_file in files:
                if a_file.endswith(".csv"):
                    csv_files.append(root + os.sep + a_file)

        for a_file in csv_files:
            a_df = pd.read_csv(a_file, '\t')
            fitness_values.append(a_df['Best'][start_generation:].max())
        return fitness_values

    def get_fitness_values_of_an_trial(self, path):
        csv_files = []
        for root, dirs, files in os.walk(path):
            for a_file in files:
                if a_file.endswith(".csv"):
                    csv_files.append(root + os.sep + a_file)

        if len(csv_files) == 1:
            a_df = pd.read_csv(csv_files[0], '\t')
            return a_df['Best'].tolist()
        else:
            raise Exception("Error: No or multiple CSV files detected. ")


