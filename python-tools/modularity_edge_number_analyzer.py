from file_processor import count_number_of_edges
from ModularityDominanceAnalyzer import ModularityDominanceAnalyzer
from file_processor import get_immediate_subdirectories
from GRNPlotter import GRNPlotter

working_path = '/Users/qin/Software-Engineering/Chin-GA-Project/thesis-data/' \
               'combined-chin-crossover'
omega = ModularityDominanceAnalyzer()
beta = GRNPlotter()

most_modular_edge_numbers = []
least_modular_edge_numbers = []

for a_directory in get_immediate_subdirectories(working_path):
    phes = beta.get_grn_phenotypes(a_directory)
    print len(phes)
    most_modular_tuple = omega.get_the_most_modular_individual_in_the_fittest_networks(a_directory, 501)
    least_modular_tuple = omega.get_the_least_modular_individual_in_the_fittest_networks(a_directory, 501)
    most_modular_edge_numbers.append(count_number_of_edges(phes[most_modular_tuple[0]]))
    least_modular_edge_numbers.append(count_number_of_edges(phes[least_modular_tuple[0]]))

print most_modular_edge_numbers
print least_modular_edge_numbers
