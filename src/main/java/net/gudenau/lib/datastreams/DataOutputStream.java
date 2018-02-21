package net.gudenau.lib.datastreams;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import net.gudenau.lib.annotation.NonNull;

/**
 * An {@link java.io.OutputStream OutputStream} wrapper that
 * provides multi-byte write operations for larger data
 * types, such as int.
 * */
// I know Idea, but this is a *library*.
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class DataOutputStream extends OutputStream{
    private final OutputStream parent;
    
    private byte[] array = new byte[Long.BYTES];
    private ByteBuffer buffer = ByteBuffer.wrap(array);
    
    /**
     * Creates a new {@link net.gudenau.lib.datastreams.DataOutputStream DataOutputStream}
     * that is big endian.
     *
     * @param parent The {@link java.io.OutputStream OutputStream} to wrap
     * */
    public DataOutputStream(@NonNull OutputStream parent){
        this(parent, ByteOrder.BIG_ENDIAN);
    }
    
    /**
     * Creates a new {@link net.gudenau.lib.datastreams.DataOutputStream DataOutputStream}
     * that uses the provided {@link java.nio.ByteOrder ByteOrder}.
     *
     * @param parent The {@link java.io.OutputStream OutputStream} to wrap
     * @param order The {@link java.nio.ByteOrder} to use
     * */
    public DataOutputStream(@NonNull OutputStream parent, @NonNull ByteOrder order){
        this.parent = parent;
        setByteOrder(order);
    }
    
    /**
     * Gets the current {@link java.nio.ByteOrder ByteOrder}.
     *
     * @return Current {@link java.nio.ByteOrder ByteOrder}
     * */
    public ByteOrder getByteOrder(){
        return buffer.order();
    }
    
    /**
     * Sets the {@link java.nio.ByteOrder ByteOrder} to use for
     * future operations.
     *
     * @param order The new order to use
     *
     * @return The stream for chaining
     * */
    public DataOutputStream setByteOrder(@NonNull ByteOrder order){
        buffer.order(order);
        return this;
    }
    
    /**
     * Writes a single byte.
     *
     * @param value The byte to write
     * */
    public void writeByte(byte value) throws IOException{
        buffer.put(0, value);
        parent.write(array, 0, Byte.BYTES);
    }
    
    /**
     * Writes a single boolean.
     *
     * @param value The value to write
     * */
    public void writeBoolean(boolean value) throws IOException{
        buffer.put(0, (byte)(value ? 1 : 0));
        parent.write(array, 0, Byte.BYTES);
    }
    
    /**
     * Writes a single short.
     *
     * @param value The value to write
     * */
    public void writeShort(short value) throws IOException{
        buffer.putShort(0, value);
        parent.write(array, 0, Short.BYTES);
    }
    
    /**
     * Writes a single character.
     *
     * @param value The value to write
     * */
    public void writeChar(char value) throws IOException{
        buffer.putChar(0, value);
        parent.write(array, 0, Character.BYTES);
    }
    
    /**
     * Writes a single integer.
     *
     * @param value The value to write
     * */
    public void writeInt(int value) throws IOException{
        buffer.putInt(0, value);
        parent.write(array, 0, Integer.BYTES);
    }
    
    /**
     * Writes a single float.
     *
     * @param value The value to write
     * */
    public void writeFloat(float value) throws IOException{
        buffer.putFloat(0, value);
        parent.write(array, 0, Float.BYTES);
    }
    
    /**
     * Writes a single long.
     *
     * @param value The value to write
     * */
    public void writeLong(long value) throws IOException{
        buffer.putLong(0, value);
        parent.write(array, 0, Long.BYTES);
    }
    
    /**
     * Writes a single double.
     *
     * @param value The value to write
     * */
    public void writeDouble(double value) throws IOException{
        buffer.putDouble(0, value);
        parent.write(array, 0, Double.BYTES);
    }
    
    /**
     * Writes a multiple bytes.
     *
     * @param values The values to write
     * */
    public void writeBytes(@NonNull byte[] values) throws IOException{
        parent.write(values, 0, values.length);
    }
    
    /**
     * Writes a multiple booleans.
     *
     * @param values The values to write
     * @param packed Should the values be packed?
     * */
    public void writeBooleans(@NonNull boolean[] values, boolean packed) throws IOException{
        writeBooleans(values, 0, values.length, packed);
    }
    
    /**
     * Writes a multiple shorts.
     *
     * @param values The values to write
     * */
    public void writeShorts(@NonNull short[] values) throws IOException{
        writeShorts(values, 0, values.length);
    }
    
    /**
     * Writes a multiple characters.
     *
     * @param values The values to write
     * */
    public void writeChars(@NonNull char[] values) throws IOException{
        writeChars(values, 0, values.length);
    }
    
    /**
     * Writes a multiple integers.
     *
     * @param values The values to write
     * */
    public void writeInts(@NonNull int[] values) throws IOException{
        writeInts(values, 0, values.length);
    }
    
    /**
     * Writes a multiple floats.
     *
     * @param values The values to write
     * */
    public void writeFloats(@NonNull float[] values) throws IOException{
        writeFloats(values, 0, values.length);
    }
    
    /**
     * Writes a multiple longs.
     *
     * @param values The values to write
     * */
    public void writeLongs(@NonNull long[] values) throws IOException{
        writeLongs(values, 0, values.length);
    }
    
    /**
     * Writes a multiple doubles.
     *
     * @param values The values to write
     * */
    public void writeDoubles(@NonNull double[] values) throws IOException{
        writeDoubles(values, 0, values.length);
    }
    
    /**
     * Writes a multiple bytes.
     *
     * @param values The values to write
     * @param offset The offset into the array to read
     * @param length The amount of values to write
     * */
    public void writeBytes(@NonNull byte[] values, int offset, int length) throws IOException{
        parent.write(values, offset, length);
    }
    
    /**
     * Writes a multiple booleans.
     *
     * @param values The values to write
     * @param offset The offset into the array to read
     * @param length The amount of values to write
     * @param packed Should the values be packed?
     * */
    public void writeBooleans(@NonNull boolean[] values, int offset, int length, boolean packed) throws IOException{
        if(packed){
            throw new UnsupportedOperationException("Packed booleans are not supported (yet)");
        }
    
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        for(int count = 0; count < length; count++){
            writeBoolean(values[offset + count]);
        }
    }
    
    /**
     * Writes a multiple shorts.
     *
     * @param values The values to write
     * @param offset The offset into the array to read
     * @param length The amount of values to write
     * */
    public void writeShorts(@NonNull short[] values, int offset, int length) throws IOException{
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        for(int count = 0; count < length; count++){
            writeShort(values[offset + count]);
        }
    }
    
    /**
     * Writes a multiple characters.
     *
     * @param values The values to write
     * @param offset The offset into the array to read
     * @param length The amount of values to write
     * */
    public void writeChars(@NonNull char[] values, int offset, int length) throws IOException{
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        for(int count = 0; count < length; count++){
            writeChar(values[offset + count]);
        }
    }
    
    /**
     * Writes a multiple integers.
     *
     * @param values The values to write
     * @param offset The offset into the array to read
     * @param length The amount of values to write
     * */
    public void writeInts(@NonNull int[] values, int offset, int length) throws IOException{
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        for(int count = 0; count < length; count++){
            writeInt(values[offset + count]);
        }
    }
    
    /**
     * Writes a multiple floats.
     *
     * @param values The values to write
     * @param offset The offset into the array to read
     * @param length The amount of values to write
     * */
    public void writeFloats(@NonNull float[] values, int offset, int length) throws IOException{
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        for(int count = 0; count < length; count++){
            writeFloat(values[offset + count]);
        }
    }
    
    /**
     * Writes a multiple longs.
     *
     * @param values The values to write
     * @param offset The offset into the array to read
     * @param length The amount of values to write
     * */
    public void writeLongs(@NonNull long[] values, int offset, int length) throws IOException{
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        for(int count = 0; count < length; count++){
            writeLong(values[offset + count]);
        }
    }
    
    /**
     * Writes a multiple doubles.
     *
     * @param values The values to write
     * @param offset The offset into the array to read
     * @param length The amount of values to write
     * */
    public void writeDoubles(@NonNull double[] values, int offset, int length) throws IOException{
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        for(int count = 0; count < length; count++){
            writeDouble(values[offset + count]);
        }
    }
    
    @Override
    public void write(int b) throws IOException{
        parent.write(b);
    }
    
    @Override
    public void write(byte b[]) throws IOException {
        parent.write(b);
    }
    
    @Override
    public void write(byte b[], int off, int len) throws IOException {
        parent.write(b, off, len);
    }
    
    @Override
    public void flush() throws IOException {
        parent.flush();
    }
    
    @Override
    public void close() throws IOException {
        parent.close();
    }
}
