import os
import pandas as pd


class CSVFileOpener:
    def __init__(self):
        pass

    def get_fitness_values_of_an_trial(self, path, column):
        csv_files = []
        for root, dirs, files in os.walk(path):
            for a_file in files:
                if a_file.endswith(".csv"):
                    csv_files.append(root + os.sep + a_file)

        if len(csv_files) == 1:
            a_df = pd.read_csv(csv_files[0], '\t')
            return a_df[column].tolist()
        else:
            raise RuntimeError("Error at " + path + ": No or multiple CSV files detected. ")