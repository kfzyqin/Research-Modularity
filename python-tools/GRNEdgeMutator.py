import math
import random

class GRNEdgeMutator:
    def __init__(self, prob=0.05):
        self.prob = prob

    def mutateAGRN(self, aGRN):
        targetNumber = int(math.sqrt(len(aGRN)))

        for i in range(targetNumber):
            regulatorNumber = 0
            if random.uniform(0, 1) > self.prob:
                continue

            for j in range(targetNumber):
                if aGRN[j * targetNumber + i] != 0:
                    regulatorNumber += 1

            probability_to_lose_interaction = \
                (4.0 * regulatorNumber) / (4.0 * regulatorNumber + (targetNumber - regulatorNumber))

            if random.uniform(0, 1) <= probability_to_lose_interaction:
                interactions = []
                for edgeIdx in range(targetNumber):
                    if aGRN[edgeIdx * targetNumber + i] != 0:
                        interactions.append(edgeIdx)
                if len(interactions) > 0:
                    toRemove = random.randint(0, len(interactions)-1)
                    try:
                        aGRN[interactions[toRemove] * targetNumber + i] = 0
                    except:
                        print interactions
                        print toRemove
                        print(interactions[toRemove] * targetNumber + i)

            else:
                non_interactions = []
                for edgeIdx in range(targetNumber):
                    if aGRN[edgeIdx * targetNumber + i] == 0:
                        non_interactions.append(edgeIdx)
                if len(non_interactions) > 0:
                    toAdd = random.randint(0, len(non_interactions)-1)
                    try:
                        if (random.uniform(0, 1) <= 0.5):
                            aGRN[non_interactions[toAdd] * targetNumber + i] = 1
                        else:
                            aGRN[non_interactions[toAdd] * targetNumber + i] = -1
                    except:
                        print non_interactions
                        print toAdd
                        print(non_interactions[toAdd] * targetNumber + i)

