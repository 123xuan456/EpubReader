package com.koolearn.klibrary.core.view;

import com.koolearn.klibrary.core.filesystem.ZLFile;
import com.koolearn.klibrary.core.fonts.FontEntry;
import com.koolearn.klibrary.core.image.ZLImageData;
import com.koolearn.klibrary.core.util.SystemInfo;
import com.koolearn.klibrary.core.util.ZLColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

abstract public class ZLPaintContext {
    private final SystemInfo mySystemInfo;
    private final ArrayList<String> myFamilies = new ArrayList<String>();

    protected ZLPaintContext(SystemInfo systemInfo) {
        mySystemInfo = systemInfo;
    }

    public enum FillMode {
        tile,
        tileMirror,
        fullscreen,
        stretch,
        tileVertically,
        tileHorizontally
    }

    protected final SystemInfo getSystemInfo() {
        return mySystemInfo;
    }

    abstract public void clear(ZLFile wallpaperFile, FillMode mode);

    abstract public void clear(ZLColor color);

    abstract public ZLColor getBackgroundColor();

    private boolean myResetFont = true;
    private List<FontEntry> myFontEntries;
    private int myFontSize;
    private boolean myFontIsBold;
    private boolean myFontIsItalic;
    private boolean myFontIsUnderlined;
    private boolean myFontIsStrikedThrough;

    public final void setFont(List<FontEntry> entries, int size, boolean bold, boolean italic, boolean underline, boolean strikeThrough) {
        if (entries != null && !entries.equals(myFontEntries)) {
            myFontEntries = entries;
            myResetFont = true;
        }
        if (myFontSize != size) {
            myFontSize = size;
            myResetFont = true;
        }
        if (myFontIsBold != bold) {
            myFontIsBold = bold;
            myResetFont = true;
        }
        if (myFontIsItalic != italic) {
            myFontIsItalic = italic;
            myResetFont = true;
        }
        if (myFontIsUnderlined != underline) {
            myFontIsUnderlined = underline;
            myResetFont = true;
        }
        if (myFontIsStrikedThrough != strikeThrough) {
            myFontIsStrikedThrough = strikeThrough;
            myResetFont = true;
        }
        if (myResetFont) {
            myResetFont = false;
            setFontInternal(myFontEntries, size, bold, italic, underline, strikeThrough);
            mySpaceWidth = -1;
            myStringHeight = -1;
            myDescent = -1;
            myCharHeights.clear();
        }
    }

    abstract protected void setFontInternal(List<FontEntry> entries, int size, boolean bold, boolean italic, boolean underline, boolean strikeThrough);

    abstract public void setTextColor(ZLColor color);

    abstract public void setLineColor(ZLColor color);

    abstract public void setLineWidth(int width);

    final public void setFillColor(ZLColor color) {
        setFillColor(color, 0xFF);
    }

    abstract public void setFillColor(ZLColor color, int alpha);

    abstract public int getWidth();

    abstract public int getHeight();

    public final int getStringWidth(String string) {
        return getStringWidth(string.toCharArray(), 0, string.length());
    }

    abstract public int getStringWidth(char[] string, int offset, int length);

    private int mySpaceWidth = -1;

    public final int getSpaceWidth() {
        int spaceWidth = mySpaceWidth;
        if (spaceWidth == -1) {
            spaceWidth = getSpaceWidthInternal();
            mySpaceWidth = spaceWidth;
        }
        return spaceWidth;
    }

    abstract protected int getSpaceWidthInternal();

    private int myStringHeight = -1;

    public final int getStringHeight() {
        int stringHeight = myStringHeight;
        if (stringHeight == -1) {
            stringHeight = getStringHeightInternal();
            myStringHeight = stringHeight;
        }
        return stringHeight;
    }

    abstract protected int getStringHeightInternal();

    private Map<Character, Integer> myCharHeights = new TreeMap<Character, Integer>();

    public final int getCharHeight(char chr) {
        final Integer h = myCharHeights.get(chr);
        if (h != null) {
            return h;
        }
        final int he = getCharHeightInternal(chr);
        myCharHeights.put(chr, he);
        return he;
    }

    protected abstract int getCharHeightInternal(char chr);

    private int myDescent = -1;

    public final int getDescent() {
        int descent = myDescent;
        if (descent == -1) {
            descent = getDescentInternal();
            myDescent = descent;
        }
        return descent;
    }

    abstract protected int getDescentInternal();

    public final void drawString(int x, int y, String string) {
        drawString(x, y, string.toCharArray(), 0, string.length());
    }

    abstract public void drawString(int x, int y, char[] string, int offset, int length);


    public static final class Size {
        public final int Width;
        public final int Height;

        public Size(int w, int h) {
            Width = w;
            Height = h;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Size)) {
                return false;
            }
            final Size s = (Size) other;
            return Width == s.Width && Height == s.Height;
        }

        @Override
        public String toString() {
            return "ZLPaintContext.Size[" + Width + "x" + Height + "]";
        }
    }

    public static enum ScalingType {
        OriginalSize,
        IntegerCoefficient,
        FitMaximum
    }

    public enum ColorAdjustingMode {
        NONE,
        DARKEN_TO_BACKGROUND,
        LIGHTEN_TO_BACKGROUND
    }

    abstract public Size imageSize(ZLImageData image, Size maxSize, ScalingType scaling);

    abstract public void drawImage(int x, int y, ZLImageData image, Size maxSize, ScalingType scaling, ColorAdjustingMode adjustingMode);

    abstract public void drawLine(int x0, int y0, int x1, int y1);

    abstract public void drawFooter(String time, String page);

    abstract public void fillRectangle(int x0, int y0, int x1, int y1);

    abstract public void drawPolygonalLine(int[] xs, int[] ys);

    abstract public void fillPolygon(int[] xs, int[] ys);

    abstract public void drawOutline(int[] xs, int[] ys);

    abstract public void fillCircle(int x, int y, int radius);
}
