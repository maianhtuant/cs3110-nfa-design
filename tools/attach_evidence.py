#!/usr/bin/env python3
"""Embed existing evidence images in marked report sections; never invent evidence."""
from pathlib import Path
ROOT = Path(__file__).resolve().parent.parent
STEPS = {8: 6, 12: 9, 16: 5}

def inventory(n):
    base = f'n{n:02}'
    items = [(f'{base}-batch', 'JFLAP Multiple Run results'),
             (f'{base}-epsilon', 'Earlier JFLAP run showing the empty-string result')]
    if n in STEPS:
        items.extend((f'{base}-step-{i:02}', f'JFLAP step {i}') for i in range(STEPS[n] + 1))
    if n == 12:
        items.extend((f'{base}-reject-step-{i:02}', f'JFLAP fourth-1 rejection, step {i}') for i in range(8))
    return items

def main():
    for n in (8, 12, 16, 20, 21):
        lines = []
        for stem, caption in inventory(n):
            matches = [ROOT / 'images' / (stem + ext) for ext in ('.png', '.jpg', '.jpeg')]
            existing = [path for path in matches if path.is_file()]
            if len(existing) > 1:
                raise ValueError(f'Keep only one image version for {stem}')
            if existing:
                path = existing[0]
                lines.append(f'![{caption}]({path.relative_to(ROOT).as_posix()})')
                print(f'Attached: {path.name}')
            else:
                lines.append(f'Pending: `{stem}.png`.')
                print(f'Missing: {stem}.png')
        report = ROOT / f'n{n:02}r.md'
        text = report.read_text()
        start, end = '<!-- evidence:start -->', '<!-- evidence:end -->'
        before, rest = text.split(start, 1)
        _, after = rest.split(end, 1)
        report.write_text(before + start + '\n\n' + '\n\n'.join(lines) + '\n\n' + end + after)
    print('Image attachment does not verify image contents or complete the personal reflection.')

if __name__ == '__main__':
    main()
