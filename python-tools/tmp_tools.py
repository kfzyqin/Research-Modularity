import GRNPlotter
from file_processor import calculate_binomial_distribution
from file_processor import ncr
import networkx as nx
import matplotlib.pyplot as plt
from StatisticsToolkit import StatisticsToolkit
from EdgeNumberTool import EdgeNumberTool
from GRNCSVReader import GRNCSVReader
from scipy.stats.stats import pearsonr
import pandas as pd
import csv

csv_path = "/Users/qin/Portal/generated-outputs/fixed-record-zhenyue-balanced-combinations-elite-p001/volcanoe.csv"

a = pd.read_csv(csv_path,
                sep='\t', quoting=csv.QUOTE_NONE)

# a = pd.read_csv("/Users/qin/Portal/generated-outputs/fixed-record-zhenyue-balanced-combinations-elite-p001/"
#                 "complete_sampling_asym_volcanoe.csv",
#                 sep='\t', quoting=csv.QUOTE_NONE)

a_raw_list = a['\"Fitness Complete Sampling ASymmetric After Edge Removing\"'].values.tolist()
print(set([float(e.replace('\"', '')) for e in a_raw_list]))

# print(a['\"Fitness Complete Sampling ASymmetric After Edge Removing\"'].values.tolist())
# print(a.columns.values)
print(a['\"GRNAfterRemovalInterModuleEdge\"'])

# str_GRNs = list([e.replace('\"', '').replace('[', '').replace(']', '').split(',') for e in a['\"GRNAfterRemovalInterModuleEdge\"']])
# num_GRNs = list(list(int(e2) for e2 in e) for e in str_GRNs)
# print(num_GRNs)
#
# csv_df_saving_name = csv_path.replace('complete_sampling_asym_volcanoe.csv', '') + 'volcanoe_grns.txt'
# outfile = open(csv_df_saving_name, "w")
# print >> outfile, "\n".join(str(i) for i in num_GRNs)
# outfile.close()

# print(a[['GRNBeforeRemovalInterModuleEdge']])



