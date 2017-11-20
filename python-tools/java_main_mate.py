import GRNPlotter
import sys
import FitnessPlotter
from file_processor import save_multiple_lists_graph

working_path = sys.argv[1]
grn_plotter = GRNPlotter.GRNPlotter()
modular_values = grn_plotter.get_module_values_of_a_trial(working_path)

fitness_plotter = FitnessPlotter.FitnessPlotter()
fitness_values = fitness_plotter.get_fitness_values_of_an_trial(working_path)

save_multiple_lists_graph([fitness_values, modular_values], ['Fitness', 'Modularity'], working_path,
                          'fitness_modularity.png')
