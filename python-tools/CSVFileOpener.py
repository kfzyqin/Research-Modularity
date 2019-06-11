import os
import pandas as pd
import file_processor as fp
import numpy as np
from GRNPlotter import GRNPlotter
import tools.io_tools as io_tools
import tools.mod_tools as mod_tools
import csv_reader


class CSVFileOpener:
    def __init__(self):
        pass

    @staticmethod
    def get_column_values_of_an_trial(path, column):
        csv_files = []
        caught = False
        for root, dirs, files in os.walk(path):
            for a_file in files:
                if a_file.endswith(".csv"):
                    csv_files.append(root + os.sep + a_file)
                    caught = True
                    break
            if caught:
                break


        if len(csv_files) == 1:
            a_df = pd.read_csv(csv_files[0], '\t')
            return a_df[column].tolist()
        else:
            pass
            # raise RuntimeError("Error at " + path + ": No or multiple CSV files detected. ")

    def get_properties_of_each_generation_in_a_whole_experiment(self, exp_path, column, sample_size=100):
        trial_dirs = fp.get_immediate_subdirectories(exp_path)
        all_trails = []
        for a_trail_dir in trial_dirs:
            a_dir_list = self.get_column_values_of_an_trial(a_trail_dir, column)
            if a_dir_list is not None:
                all_trails.append(np.array(a_dir_list))
            if len(all_trails) > sample_size:
                break
        return np.mean(np.array(all_trails), axis=0)

    def get_properties_of_each_generation_in_a_whole_experiment_with_stdev(self, exp_path, column, sample_size=100):
        trial_dirs = fp.get_immediate_subdirectories(exp_path)
        all_trails = []
        for a_trail_dir in trial_dirs:
            a_dir_list = self.get_column_values_of_an_trial(a_trail_dir, column)
            if a_dir_list is not None:
                all_trails.append(np.array(a_dir_list))
            if len(all_trails) > sample_size:
                break
        print 'len of all trials: ', len(all_trails)
        return np.mean(np.array(all_trails), axis=0), np.std(np.array(all_trails), axis=0)

    def get_fittest_mod_of_a_exp_with_stdev(self, exp_path, index=-1, sample_size=100):
        trial_dirs = fp.get_immediate_subdirectories(exp_path)
        all_trails = []

        for a_trail_dir in trial_dirs:
            grn_plotter = GRNPlotter()
            phenotypes = grn_plotter.get_grn_phenotypes(a_trail_dir)
            a_dir_list = self.get_column_values_of_an_trial(a_trail_dir, 'FittestModularity')
            if a_dir_list is not None and phenotypes:
                trial_edges = list([io_tools.count_number_of_edges(a_phe) for a_phe in phenotypes])
                normed_qs = mod_tools.normalize_mod_q(a_dir_list, trial_edges)
                all_trails.append(np.array(normed_qs))
                if len(all_trails) > sample_size:
                    break
        return np.array(all_trails)[:, index], np.std(np.array(all_trails)[:, index])

    def get_mod_of_each_generation_in_a_whole_exp_with_stdev(self, exp_path, column, sample_size=100):
        trial_dirs = fp.get_immediate_subdirectories(exp_path)
        all_trails = []

        # grn_plotter = GRNPlotter()
        # all_trial_fits = []
        # edge_nums_1 = grn_plotter.get_exp_fittest_grn_edge_num(exp_path, index=index)
        # edge_nums_2 = grn_plotter.get_exp_fittest_grn_edge_num(self.path_2, index=index)
        # for a_gen in (0, 2000):
        #     a_trial_mod = csv_reader.get_entry_values_of_an_experiment(exp_path, column, a_gen)
        #
        #     all_trial_fits.append()
        #
        # return np.mean(np.array(all_trial_fits), axis=1), np.std(np.array(all_trial_fits), axis=1)

        for a_trail_dir in trial_dirs:
            grn_plotter = GRNPlotter()
            phenotypes = grn_plotter.get_grn_phenotypes(a_trail_dir)
            a_dir_list = self.get_column_values_of_an_trial(a_trail_dir, column)
            if a_dir_list is not None and phenotypes:
                trial_edges = list([io_tools.count_number_of_edges(a_phe) for a_phe in phenotypes])
                normed_qs = mod_tools.normalize_mod_q(a_dir_list, trial_edges)
                all_trails.append(np.array(normed_qs))
                if len(all_trails) > sample_size:
                    break
        return np.mean(np.array(all_trails), axis=0), np.std(np.array(all_trails), axis=0)

    def get_edge_num_of_each_generation_in_a_whole_exp_with_stdev(self, exp_path, sample_size=100):
        trial_dirs = fp.get_immediate_subdirectories(exp_path)
        all_trails = []

        for a_trail_dir in trial_dirs:
            grn_plotter = GRNPlotter()
            phenotypes = grn_plotter.get_grn_phenotypes(a_trail_dir)
            if phenotypes:
                trial_edges = list([io_tools.count_number_of_edges(a_phe) for a_phe in phenotypes])
                if len(trial_edges) == 2001:
                    all_trails.append(np.array(trial_edges))
                    if len(all_trails) > sample_size:
                        break
        return np.mean(np.array(all_trails), axis=0), np.std(np.array(all_trails), axis=0)

if __name__ == '__main__':
    pass
