import GRNPlotter
import sys
import FitnessPlotter
from file_processor import save_multiple_lists_graph
import ModularityDominanceAnalyzer
from file_processor import count_number_of_edges

working_path = sys.argv[1]
# working_path = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/' \
#                'python-autonomous-test/2017-11-21-09-52-33'
grn_plotter = GRNPlotter.GRNPlotter()

phenotypes = grn_plotter.get_grn_phenotypes(working_path)

modular_values = grn_plotter.get_module_values_of_a_trial(working_path)

fitness_plotter = FitnessPlotter.FitnessPlotter()
fitness_values = fitness_plotter.get_fitness_values_of_an_trial(working_path)

multiple_pattern_generation = int(sys.argv[2])

modularity_dominance_analyzer = ModularityDominanceAnalyzer.ModularityDominanceAnalyzer()
most_modular = modularity_dominance_analyzer.get_the_fittest_individual_in_the_most_modular_networks(
    working_path, multiple_pattern_generation)
least_modular = modularity_dominance_analyzer.get_the_least_modular_individual_in_the_fittest_networks(
    working_path, multiple_pattern_generation)

grn_plotter.draw_a_grn(phenotypes[most_modular[0]], save_path=working_path,
                       file_name='most_modular'
                                 + '_' + str(round(most_modular[1], 2)) + '_' + str(round(most_modular[2], 2)) + '_' +
                                 str(count_number_of_edges(phenotypes[most_modular[0]])) + '.png')
grn_plotter.draw_a_grn(phenotypes[least_modular[0]], save_path=working_path,
                       file_name='least_modular'
                                 + '_' + str(round(least_modular[1], 2)) + '_' + str(round(least_modular[2], 2)) + '_' +
                                 str(count_number_of_edges(phenotypes[least_modular[0]])) + '.png')

save_multiple_lists_graph([fitness_values, modular_values], ['Fitness', 'Modularity'], working_path,
                          'fitness_modularity.png', vertical_lines=[most_modular[0], least_modular[0]])

modularity_dominance_analyzer.get_edge_number_trend(working_path)
