import sys
from GRNPlotter import GRNPlotter

grn_plotter = GRNPlotter()
working_path = sys.argv[1]
target_generation = int(sys.argv[2])
# working_path = "/Users/qin/Software-Engineering/Chin-GA-Project/generated-outputs/larson-with-perturbation-recording/2018-01-13-12-16-15"
# print working_path
phenotypes = grn_plotter.get_grn_phenotypes(working_path)
print str(phenotypes[target_generation])[1:-1]
