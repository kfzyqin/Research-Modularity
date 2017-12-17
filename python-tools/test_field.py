import GRNPlotter
import ModularityDominanceAnalyzer
from file_processor import write_a_list_into_a_file

working_path = '/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/' \
               'all-combination-perturbations/2017-12-04-10-50-27'
grn_plotter = GRNPlotter.GRNPlotter()

phenotypes = grn_plotter.get_grn_phenotypes(working_path)

modularity_dominance_analyzer = ModularityDominanceAnalyzer.ModularityDominanceAnalyzer()

least_modular = modularity_dominance_analyzer.get_the_least_modular_individual_in_the_fittest_networks(
    working_path, 501)

print phenotypes[least_modular[0]]

write_a_list_into_a_file(phenotypes[least_modular[0]], working_path, 'least_modular_phenotype.phe')

converted_phenotype = modularity_dominance_analyzer.get_modular_grn_matrix(phenotypes[least_modular[0]])
write_a_list_into_a_file(converted_phenotype, working_path, 'converted_least_modular_phenotype.phe')
