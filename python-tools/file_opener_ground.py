from CSVFileOpener import CSVFileOpener
import file_processor as fp
import os
import statistics

prefix_path = os.path.expanduser("~")


def plot_fit_lines(path_1, path_2, save_path, sample_size=100):
    csv_file_opener = CSVFileOpener()
    fits_best_1, fits_std_1 = csv_file_opener.\
        get_properties_of_each_generation_in_a_whole_experiment_with_stdev(path_1, 'Best', sample_size=sample_size)
    print('len of fits_best 1: ', len(fits_best_1))
    fits_best_2, fits_std_2 = csv_file_opener.\
        get_properties_of_each_generation_in_a_whole_experiment_with_stdev(path_2, 'Best', sample_size=sample_size)
    print('len of fits_best 2: ', len(fits_best_2))

    fp.save_lists_graph([fits_best_1, fits_best_2],
                        labels=['Dist Sym Fitness', 'Dist Asym Fitness'],
                        ver_lines=[500], path=save_path, file_name='distributional_fit.png', marker='.', colors=None,
                        dpi=500, to_normalize=False, x_gap=10, error_bars=[fits_std_1, fits_std_2])


path_1 = '/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/distributional-p00'
path_2 = '/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/distributional-p01'

save_path = '/media/zhenyue-qin/New Volume/Data-Warehouse/Project-Maotai-Modularity/tec-data/tec-imgs'

sample_size = 100

plot_fit_lines(path_1, path_2, save_path, sample_size)

# # fits_avg_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'Median')
# # fits_avg_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'Median')
#
# mod_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'FittestModularity')
# mod_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'FittestModularity')
#
# fp.save_lists_graph([mod_1, mod_2], labels=['Distributional', 'Stochastic'], ver_lines=[500], path=path_1, file_name='avg_mod_comp_esw_new_p01_balanced.png', marker='.',
#                     colors=None, dpi=500, to_normalize=False)
#
# # fp.save_lists_graph([mod_1, mod_2], labels=['With Selection', 'No Selection'], ver_lines=None, path=path_1, file_name='avg_mod_perfect_module.png', marker='.',
# #                     colors=None, dpi=500, to_normalize=False)
#
# avg_edge_no_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'AvgEdgeNumber')
# avg_edge_no_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'AvgEdgeNumber')
#
# fp.save_lists_graph([avg_edge_no_1, avg_edge_no_2], labels=['Distributional', 'Stochastic'], ver_lines=[500], path=path_1, file_name='avg_edge_comp_esw_new_p01_balanced.png', marker='.',
#                     colors=None, dpi=500, to_normalize=False)
#
# # std_dev_edge_no_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'StdDevEdgeNumber')
# # std_dev_edge_no_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'StdDevEdgeNumber')
# #
# # fp.save_lists_graph([fits_avg_1, fits_avg_2], labels=['Symmetry', 'Asymmetry'], ver_lines=[500], path=path_1, file_name='avg_median_fit_sym_asym_01.png', marker='.',
# #                     colors=None, dpi=500, to_normalize=False)
#
# #
# # fp.save_lists_graph([std_dev_edge_no_1, std_dev_edge_no_2], labels=['Symmetry', 'Asymmetry'], ver_lines=[500], path=path_1, file_name='std_dev_edge_no_sym_asym_01.png', marker='.',
# #                     colors=None, dpi=500, to_normalize=False)
