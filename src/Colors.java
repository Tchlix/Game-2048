import java.awt.Color;

public enum Colors {
    _0   (0,0xcdc0b4),
    _2   (2,0xeee4da),
    _4   (4,0xede0c8),
    _8   (8,0xf2b179),
    _16  (16,0xf59563),
    _32  (32,0xf67c5f),
    _64  (64,0xf65e3b),
    _128 (128,0xedcf72),
    _256 (256,0xedcc61),
    _512 (512,0xedc850),
    _1024(1024,0xedc53f),
    _2048(2048,0xedc22e);
    private final Color color;
    private final int value;

    Colors(int v,int c)
    {
        value = v;
        color = new Color(c);
    }
    public int value(){
        return value;
    }
    public Color color(){
        return color;
    }
}
