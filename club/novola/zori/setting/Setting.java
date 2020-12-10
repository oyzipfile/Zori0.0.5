// 
// Decompiled by Procyon v0.5.36
// 

package club.novola.zori.setting;

import club.novola.zori.module.Module;

public class Setting<T>
{
    private final String name;
    private final Module parent;
    private T value;
    private T min;
    private T max;
    
    public Setting(final String name, final T value, final Module parent) {
        this.name = name;
        this.value = value;
        this.parent = parent;
    }
    
    public Setting(final String name, final T value, final T min, final T max, final Module parent) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.parent = parent;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Module getParent() {
        return this.parent;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public T getMin() {
        return this.min;
    }
    
    public T getMax() {
        return this.max;
    }
    
    public T setValue(final T value) {
        return this.value = value;
    }
}
