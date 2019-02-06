from CSVFileOpener import CSVFileOpener
import file_processor as fp

path_1 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/with-selection-two-targets'
path_2 = '/Volumes/Qin-Warehouse/Warehouse-Data/Modularity-Data/Maotai-Project-Symmetry-Breaking/generated-outputs/no-selection-two-targets'

csv_file_opener = CSVFileOpener()
fits_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'Best')
fits_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'Best')

mod_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'MostModFitness')
mod_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'MostModFitness')

avg_edge_no_1 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_1, 'AvgEdgeNumber')
avg_edge_no_2 = csv_file_opener.get_properties_of_each_generation_in_a_whole_experiment(path_2, 'AvgEdgeNumber')

fp.save_lists_graph([fits_1, fits_2], labels=['with_selection', 'no_selection'], ver_lines=None, path=path_1, file_name='avg_fit', marker='.',
                    colors=None, dpi=500, to_normalize=False)

fp.save_lists_graph([mod_1, mod_2], labels=['with_selection', 'no_selection'], ver_lines=None, path=path_1, file_name='avg_mod', marker='.',
                    colors=None, dpi=500, to_normalize=False)

fp.save_lists_graph([avg_edge_no_1, avg_edge_no_2], labels=['with_selection', 'no_selection'], ver_lines=None, path=path_1, file_name='avg_edge_no', marker='.',
                    colors=None, dpi=500, to_normalize=False)
