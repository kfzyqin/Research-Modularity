from CSVFileOpener import CSVFileOpener
import file_processor as fp

# path_1 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/with-selection-two-targets'
# path_2 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/no-selection-two-targets'

path_1 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/fixed-record-zhenyue-balanced-combinations-elite-p00'
path_2 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/fixed-record-zhenyue-balanced-combinations-elite-p01'
# path_2 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/record-esw-balanced-combinations-p00'

sample_size = 100

csv_file_opener = CSVFileOpener()
fits_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'Best')
fits_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'Best')

fits_avg_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'Median')
fits_avg_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'Median')

mod_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'FittestModularity')
mod_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'FittestModularity')

avg_edge_no_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'AvgEdgeNumber')
avg_edge_no_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'AvgEdgeNumber')

std_dev_edge_no_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'StdDevEdgeNumber')
std_dev_edge_no_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'StdDevEdgeNumber')

fp.save_lists_graph([fits_1, fits_2], labels=['Symmetry', 'Asymmetry'], ver_lines=[500], path=path_1, file_name='elite_avg_fit_sym_asym.png', marker='.',
                    colors=None, dpi=500, to_normalize=False)

fp.save_lists_graph([fits_avg_1, fits_avg_2], labels=['Symmetry', 'Asymmetry'], ver_lines=[500], path=path_1, file_name='elite_avg_median_fit_sym_asym.png', marker='.',
                    colors=None, dpi=500, to_normalize=False)

fp.save_lists_graph([mod_1, mod_2], labels=['Symmetry', 'Asymmetry'], ver_lines=[500], path=path_1, file_name='elite_avg_mod_sym_asym.png', marker='.',
                    colors=None, dpi=500, to_normalize=False)

fp.save_lists_graph([avg_edge_no_1, avg_edge_no_2], labels=['Symmetry', 'Asymmetry'], ver_lines=[500], path=path_1, file_name='elite_avg_edge_no_sym_asym.png', marker='.',
                    colors=None, dpi=500, to_normalize=False)

fp.save_lists_graph([std_dev_edge_no_1, std_dev_edge_no_2], labels=['Symmetry', 'Asymmetry'], ver_lines=[500], path=path_1, file_name='elite_std_dev_edge_no_sym_asym.png', marker='.',
                    colors=None, dpi=500, to_normalize=False)
