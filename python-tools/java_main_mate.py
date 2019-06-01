import GRNPlotter
import sys
import GRNCSVReader
from file_processor import save_multiple_lists_graph, write_a_list_into_a_file
import ModularityDominanceAnalyzer
from file_processor import count_number_of_edges
import CSVFileOpener
from file_processor import get_immediate_subdirectories

working_path = sys.argv[1]
# working_path = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/' \
#                'test-complete-record/2018-01-13-11-00-19'

# directories = get_immediate_subdirectories('/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/change-to-tournament-selection')

# for working_path in directories:

grn_plotter = GRNPlotter.GRNPlotter()

phenotypes = grn_plotter.get_grn_phenotypes(working_path)

modular_values = grn_plotter.get_module_values_of_a_trial(working_path)

fitness_plotter = GRNCSVReader.GRNCSVReader()
fitness_values = fitness_plotter.get_fitness_values_of_an_trial(working_path)

multiple_pattern_generation = int(sys.argv[2])
# multiple_pattern_generation = 50

modularity_dominance_analyzer = ModularityDominanceAnalyzer.ModularityDominanceAnalyzer()
most_modular = modularity_dominance_analyzer.get_the_fittest_individual_in_the_most_modular_networks(
    working_path, multiple_pattern_generation)
least_modular = modularity_dominance_analyzer.get_the_least_modular_individual_in_the_fittest_networks(
    working_path, multiple_pattern_generation)

grn_plotter.draw_a_grn(phenotypes[most_modular[0]], save_path=working_path,
                       file_name='most_modular'
                                 + '_' + str(round(most_modular[1], 3)) + '_' + str(round(most_modular[2], 3)) + '_' +
                                 str(count_number_of_edges(phenotypes[most_modular[0]])) + '.png')
grn_plotter.draw_a_grn(phenotypes[least_modular[0]], save_path=working_path,
                       file_name='least_modular'
                                 + '_' + str(round(least_modular[1], 3)) + '_' + str(round(least_modular[2], 3)) + '_' +
                                 str(count_number_of_edges(phenotypes[least_modular[0]])) + '.png')

save_multiple_lists_graph([fitness_values, modular_values], ['Fitness', 'Modularity'], working_path,
                          'fitness_modularity.png', vertical_lines=[most_modular[0], least_modular[0]])

csv_file_opener = CSVFileOpener.CSVFileOpener()
average_edge_numbers = csv_file_opener.get_column_values_of_an_trial(working_path, 'AvgEdgeNumber')
std_dev_numbers = csv_file_opener.get_column_values_of_an_trial(working_path, 'StdDevEdgeNumber')

save_multiple_lists_graph([average_edge_numbers, std_dev_numbers], ['Average Edge Number', 'Std Dev Number'], working_path,
                          'avg_edge_num_and_std_dev.png', vertical_lines=[most_modular[0], least_modular[0]])

least_modular_gen = []
least_modular_gen.append(str(least_modular[0]))

write_a_list_into_a_file(least_modular_gen, working_path, 'least_modular_generation.gen')

write_a_list_into_a_file(phenotypes[least_modular[0]], working_path, 'least_modular_phenotype.phe')

converted_phenotype = modularity_dominance_analyzer.get_modular_grn_matrix(phenotypes[least_modular[0]])
write_a_list_into_a_file(converted_phenotype, working_path, 'converted_least_modular_phenotype.phe')
