import storage.edge_num_shading as edge_num_shading
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np

fig, ax = plt.subplots()
plt.xlabel('')
plt.ylabel('')
the_y_tick_labels = list(range(15, 36))
# sns.heatmap(x_res, square=True, ax=ax, cmap=sns.dark_palette("grey", reverse=True), yticklabels=False)
an_edge_num_gen = np.array(edge_num_shading.dist_p00_edge_num_shading).transpose()
sns.heatmap(an_edge_num_gen, yticklabels=the_y_tick_labels, cmap="CMRmap").invert_yaxis()
plt.xticks(fontsize=12)
plt.legend(loc='lower center')
plt.tight_layout()
plt.show()
