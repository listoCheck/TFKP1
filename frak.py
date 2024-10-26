from PIL import Image
import numpy as np


def mondel():
    max_iterations = 1024
    a = 2
    x_pos, y_pos = -2, -2
    width, height = 1024, 1024

    def mandelbrot(x1, y1):
        x, y = 0, 0
        iter = 0
        while x * x + y * y < 4 and iter < max_iterations:
            temp = x * x - y * y + x1
            y = 2.0 * x * y + y1
            x = temp
            iter += 1
        return max_iterations - iter

    def wbgradColor(stop, value, rev):
        if stop == 1:
            return 0
        middle = stop / 2
        if value > middle:
            return wbgradColor(stop - middle, value - middle, not rev)
        k = 256 / middle
        if rev:
            return round((middle - value) * k)
        return round(value * k)

    img = Image.new("RGB", (width, height), "white")
    pixels = img.load()
    for px in range(width):
        for py in range(height):
            x0 = (px / width) * 2 * a + x_pos
            y0 = (py / height) * 2 * a + y_pos
            iter_count = mandelbrot(x0, y0)
            gray_val = wbgradColor(max_iterations, iter_count, True)
            pixels[px, py] = (gray_val, gray_val, gray_val)
    image = img.convert("P")
    image.putpalette(palette)
    img.save("mandelbrot.png")
    image.show()


def budda():
    width, height = 800, 800
    max_iter = 1000
    zoom = 250
    budda_brot = np.zeros((height, width))

    def calculate_buddabrot():
        for y_c in range(width):
            for x_c in range(height):
                x0, y0 = 0, 0
                cx = (x_c - width / 2) / zoom
                cy = (y_c - height / 2) / zoom
                X = []
                Y = []
                escaped = False
                for _ in range(max_iter):
                    abszx2 = x0 * x0
                    abszy2 = y0 * y0
                    if abszx2 + abszy2 > 4.0:
                        escaped = True
                        break
                    X.append(x0)
                    Y.append(y0)
                    y0 = 2.0 * x0 * y0 + cy
                    x0 = abszx2 - abszy2 + cx
                if escaped:
                    for i in range(len(X)):
                        px = int((X[i] * zoom) + width / 2)
                        py = int((Y[i] * zoom) + height / 2)
                        if 0 <= px < width and 0 <= py < height:
                            budda_brot[py, px] += 1

    calculate_buddabrot()
    log_budda_brot = np.log1p(budda_brot)
    normalized_brot = (255 * log_budda_brot / log_budda_brot.max() * 3).astype(np.uint8)
    image = Image.fromarray(normalized_brot)
    image = image.convert("P")
    image.putpalette(palette)
    image.save("budda_brot_inferno.png")
    image.show()


inferno_palette = [
    (0, 0, 0), (31, 12, 72), (85, 15, 109), (136, 34, 106), (186, 54, 85),
    (227, 89, 51), (249, 140, 10), (253, 196, 49), (247, 247, 180), (255, 255, 255)
]
palette = []
for i in range(256):
    t = i / 255 * (len(inferno_palette) - 1)
    c1 = inferno_palette[int(t)]
    c2 = inferno_palette[min(int(t) + 1, len(inferno_palette) - 1)]
    r = int(c1[0] + (c2[0] - c1[0]) * (t % 1))
    g = int(c1[1] + (c2[1] - c1[1]) * (t % 1))
    b = int(c1[2] + (c2[2] - c1[2]) * (t % 1))
    palette.extend((r, g, b))
mondel()
budda()
