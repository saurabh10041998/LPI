class Board:
    def __init__(self, arr, level, size):
        self.size = size
        self.gx = level
        self.tiles = []
        for item in arr:
            self.tiles.append(item)
        self.hx = self.calculateHx()
    
    def calculateHx(self):
        mis_matched_tiles = 0
        for i in range(len(self.tiles)):
            if self.tiles[i] != i + 1:
                mis_matched_tiles += 1
        return mis_matched_tiles
    
    def getGx(self):
        return self.gx

    def getHx(self):
        return self.hx