import FitnessPlotter
import GRNPlotter
from file_processor import calculate_binomial_distribution
from file_processor import ncr
import networkx as nx
import matplotlib.pyplot as plt

a = [0, 1, 0, 0, 0, 0,
     0, 0, 0, 0, 0, 0,
     0, 0, 0, 0, 0, 0,
     0, 0, 0, 0, 1, 0,
     0, 0, 0, 1, 0, 0,
     0, 0, 0, 0, 0, 0]

grn_plotter = GRNPlotter.GRNPlotter()

a_grn = grn_plotter.generate_directed_grn(a)

print grn_plotter.get_modularity_value(a_grn)

# grn_plotter.draw_a_grn(a_grn, is_to_save=False)