import sys
from GRNPlotter import GRNPlotter

grn_plotter = GRNPlotter()
# working_path = sys.argv[1]
# target_generation = int(sys.argv[2])

working_path = "/Volumes/Chin-Soundwave/Chin-GA-Project/generated-outputs/larson-with-perturbation-recording/2018-01-20-17-34-58"
print working_path
target_generation = -1

phenotypes = grn_plotter.get_grn_phenotypes(working_path)
print str(phenotypes[target_generation])[1:-1]
