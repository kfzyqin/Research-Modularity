import pandas as pd
import os
import csv_reader


class GRNCSVReader:
    def __init__(self):
        pass

    @staticmethod
    def get_fitness_values_of_an_experiment(root_directory_path, index=-1):
        return csv_reader.get_entry_values_of_an_experiment(root_directory_path, 'Best', index=index)

    @staticmethod
    def get_best_fitness_values_of_an_exeriment(root_directory_path, start_generation=501):
        return csv_reader.get_best_entry_values_of_an_exeriment(root_directory_path, 'Best', start_generation)

    @staticmethod
    def get_fitness_values_of_an_trial(path):
        return csv_reader.get_entry_values_of_an_trial(path, 'Best')

    @staticmethod
    def get_most_modularities_of_an_experiment(root_directory_path, index=-1):
        return csv_reader.get_entry_values_of_an_experiment(root_directory_path, 'MostModularity', index=index)

    @staticmethod
    def get_fittest_modularities_of_an_experiment(root_directory_path, index=-1):
        return csv_reader.get_entry_values_of_an_experiment(root_directory_path, 'FittestModularity', index=index)



