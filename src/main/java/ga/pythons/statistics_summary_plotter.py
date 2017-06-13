import pandas as pd
import matplotlib.pyplot as plt

data = pd.read_csv("../gasee-chin/gasee/Simple-Haploid-10-Targets-GRN.csv", sep='\t')

print data

data.plot()

# x = data.index.values
# y = data['Best']
#
# plt.plot(x,y)
plt.show()
