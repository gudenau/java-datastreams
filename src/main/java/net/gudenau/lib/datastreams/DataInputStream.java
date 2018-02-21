package net.gudenau.lib.datastreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import net.gudenau.lib.annotation.NonNull;

/**
 * An {@link java.io.InputStream InputStream} wrapper that
 * provides multi-byte read operations for larger data
 * types, such as int.
 * */
// I know Idea, but this is a *library*.
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class DataInputStream extends InputStream{
    private final InputStream parent;
    
    private byte[] array = new byte[Long.BYTES];
    private ByteBuffer buffer = ByteBuffer.wrap(array);
    
    /**
     * Creates a new {@link net.gudenau.lib.datastreams.DataInputStream DataInputStream}
     * that is big endian.
     *
     * @param parent The {@link java.io.InputStream InputStream} to wrap
     * */
    public DataInputStream(@NonNull InputStream parent){
        this(parent, ByteOrder.BIG_ENDIAN);
    }
    
    /**
     * Creates a new {@link net.gudenau.lib.datastreams.DataInputStream DataInputStream}
     * that uses the provided {@link java.nio.ByteOrder ByteOrder}.
     *
     * @param parent The {@link java.io.InputStream InputStream} to wrap
     * @param order The {@link java.nio.ByteOrder} to use
     * */
    public DataInputStream(@NonNull InputStream parent, @NonNull ByteOrder order){
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
    public DataInputStream setByteOrder(@NonNull ByteOrder order){
        buffer.order(order);
        return this;
    }
    
    /**
     * Reads a single byte.
     *
     * @return The read byte
     * */
    public byte readByte() throws IOException{
        readNBytes(array, 0, Byte.BYTES);
        return array[0];
    }
    
    /**
     * Reads a single boolean.
     *
     * @return The read boolean
     * */
    public boolean readBoolean() throws IOException{
        readNBytes(array, 0, Byte.BYTES);
        return array[0] != 0;
    }
    
    /**
     * Reads a single short.
     *
     * @return The read short
     * */
    public short readShort() throws IOException{
        readNBytes(array, 0, Short.BYTES);
        return buffer.getShort(0);
    }
    
    /**
     * Reads a single character.
     *
     * @return The read character
     * */
    public char readChar() throws IOException{
        readNBytes(array, 0, Character.BYTES);
        return buffer.getChar(0);
    }
    
    /**
     * Reads a single integer.
     *
     * @return The read integer
     * */
    public int readInt() throws IOException{
        readNBytes(array, 0, Integer.BYTES);
        return buffer.getInt(0);
    }
    
    /**
     * Reads a single float.
     *
     * @return The read float
     * */
    public float readFloat() throws IOException{
        readNBytes(array, 0, Float.BYTES);
        return buffer.getFloat(0);
    }
    
    /**
     * Reads a single long.
     *
     * @return The read long
     * */
    public long readLong() throws IOException{
        readNBytes(array, 0, Long.BYTES);
        return buffer.getLong(0);
    }
    
    /**
     * Reads a single double.
     *
     * @return The read double
     * */
    public double readDouble() throws IOException{
        readNBytes(array, 0, Double.BYTES);
        return buffer.getDouble(0);
    }
    
    /**
     * Reads multiple bytes.
     *
     * @param values The array to read to.
     *
     * @return The count of read values
     * */
    public int readBytes(@NonNull byte[] values) throws IOException{
        return readNBytes(values, 0, values.length);
    }
    
    /**
     * Reads multiple booleans.
     *
     * @param values The array to read to
     * @param packed Is the data packed?
     *
     * @return The amount of read values
     * */
    public int readBooleans(@NonNull boolean[] values, boolean packed) throws IOException{
        return readBooleans(values, 0, values.length, packed);
    }
    
    /**
     * Reads multiple shorts.
     *
     * @param values The array to read to
     *
     * @return The amount of read values
     * */
    public int readShorts(@NonNull short[] values) throws IOException{
        return readShorts(values, 0, values.length);
    }
    
    /**
     * Reads multiple characters.
     *
     * @param values The array to read to
     *
     * @return The amount of read values
     * */
    public int readChars(@NonNull char[] values) throws IOException{
        return readChars(values, 0, values.length);
    }
    
    /**
     * Reads multiple integers.
     *
     * @param values The array to read to
     *
     * @return The amount of read values
     * */
    public int readInts(@NonNull int[] values) throws IOException{
        return readInts(values, 0, values.length);
    }
    
    /**
     * Reads multiple floats.
     *
     * @param values The array to read to
     *
     * @return The amount of read values
     * */
    public int readFloats(@NonNull float[] values) throws IOException{
        return readFloats(values, 0, values.length);
    }
    
    /**
     * Reads multiple longs.
     *
     * @param values The array to read to
     *
     * @return The amount of read values
     * */
    public int readLongs(@NonNull long[] values) throws IOException{
        return readLongs(values, 0, values.length);
    }
    
    /**
     * Reads multiple doubles.
     *
     * @param values The array to read to
     *
     * @return The amount of read values
     * */
    public int readDoubles(@NonNull double[] values) throws IOException{
        return readDoubles(values, 0, values.length);
    }
    
    /**
     * Reads multiple bytes.
     *
     * @param values The array to read to
     * @param offset The offset in the array to start
     * @param length The amount of elements to read
     *
     * @return The amount of read values
     * */
    public int readBytes(@NonNull byte[] values, int offset, int length) throws IOException{
        return read(values, offset, length);
    }
    
    /**
     * Reads multiple booleans.
     *
     * @param values The array to read to
     * @param offset The offset in the array to start
     * @param length The amount of elements to read
     * @param packed Is the stream packed?
     *
     * @return The amount of read values
     * */
    public int readBooleans(@NonNull boolean[] values, int offset, int length, boolean packed) throws IOException{
        if(packed){
            throw new UnsupportedOperationException("Packed booleans are not supported (yet)");
        }
    
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        int read;
        for(int count = 0; count < length; count++){
            if((read = readNBytes(array, 0, Byte.BYTES)) < Byte.BYTES){
                fillRemaining(array, read, Byte.BYTES);
            }
            values[offset + count] = buffer.get(0) != 0;
        }
        return length;
    }
    
    /**
     * Fills the remaining bytes with 0.
     *
     * @param array The array
     * @param amount The amount to fill
     * @param dataSize The size of the data
     * */
    private void fillRemaining(byte[] array, int amount, int dataSize){
        for(int i = dataSize - amount; i < dataSize; i++){
            array[i] = 0;
        }
    }
    
    /**
     * Reads multiple shorts.
     *
     * @param values The array to read to
     * @param offset The offset in the array to start
     * @param length The amount of elements to read
     *
     * @return The amount of read values
     * */
    public int readShorts(@NonNull short[] values, int offset, int length) throws IOException{
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        int read;
        for(int count = 0; count < length; count++){
            if((read = readNBytes(array, 0, Short.BYTES)) < Short.BYTES){
                fillRemaining(array, read, Short.BYTES);
            }
            values[offset + count] = buffer.getShort(0);
        }
        return length;
    }
    
    /**
     * Reads multiple characters.
     *
     * @param values The array to read to
     * @param offset The offset in the array to start
     * @param length The amount of elements to read
     *
     * @return The amount of read values
     * */
    public int readChars(@NonNull char[] values, int offset, int length) throws IOException{
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        int read;
        for(int count = 0; count < length; count++){
            if((read = readNBytes(array, 0, Character.BYTES)) < Character.BYTES){
                fillRemaining(array, read, Character.BYTES);
            }
            values[offset + count] = buffer.getChar(0);
        }
        return length;
    }
    /**
     * Reads multiple integers.
     *
     * @param values The array to read to
     * @param offset The offset in the array to start
     * @param length The amount of elements to read
     *
     * @return The amount of read values
     * */
    public int readInts(@NonNull int[] values, int offset, int length) throws IOException{
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        int read;
        for(int count = 0; count < length; count++){
            if((read = readNBytes(array, 0, Integer.BYTES)) < Integer.BYTES){
                fillRemaining(array, read, Integer.BYTES);
            }
            values[offset + count] = buffer.getInt(0);
        }
        return length;
    }
    
    /**
     * Reads multiple floats.
     *
     * @param values The array to read to
     * @param offset The offset in the array to start
     * @param length The amount of elements to read
     *
     * @return The amount of read values
     * */
    public int readFloats(@NonNull float[] values, int offset, int length) throws IOException{
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        int read;
        for(int count = 0; count < length; count++){
            if((read = readNBytes(array, 0, Float.BYTES)) < Float.BYTES){
                fillRemaining(array, read, Float.BYTES);
            }
            values[offset + count] = buffer.getFloat(0);
        }
        return length;
    }
    
    /**
     * Reads multiple longs.
     *
     * @param values The array to read to
     * @param offset The offset in the array to start
     * @param length The amount of elements to read
     *
     * @return The amount of read values
     * */
    public int readLongs(@NonNull long[] values, int offset, int length) throws IOException{
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        int read;
        for(int count = 0; count < length; count++){
            if((read = readNBytes(array, 0, Long.BYTES)) < Long.BYTES){
                fillRemaining(array, read, Long.BYTES);
            }
            values[offset + count] = buffer.getLong(0);
        }
        return length;
    }
    
    /**
     * Reads multiple doubles.
     *
     * @param values The array to read to
     * @param offset The offset in the array to start
     * @param length The amount of elements to read
     *
     * @return The amount of read values
     * */
    public int readDoubles(@NonNull double[] values, int offset, int length) throws IOException{
        if (offset < 0 || length < 0 || length > values.length - offset){
            throw new IndexOutOfBoundsException();
        }
    
        int read;
        for(int count = 0; count < length; count++){
            if((read = readNBytes(array, 0, Double.BYTES)) < Double.BYTES){
                fillRemaining(array, read, Double.BYTES);
            }
            values[offset + count] = buffer.getDouble(0);
        }
        return length;
    }
    
    @Override
    public int read() throws IOException{
        return parent.read();
    }
    
    @Override
    public int read(byte b[]) throws IOException {
        return parent.read(b);
    }
    
    @Override
    public int read(byte b[], int off, int len) throws IOException {
        return parent.read(b, off, len);
    }
    
    @Override
    public byte[] readAllBytes() throws IOException {
        return parent.readAllBytes();
    }
    
    @Override
    public int readNBytes(byte[] b, int off, int len) throws IOException {
        return parent.readNBytes(b, off, len);
    }
    
    @Override
    public long skip(long n) throws IOException {
        return parent.skip(n);
    }
    
    @Override
    public int available() throws IOException {
        return parent.available();
    }
    
    @Override
    public void close() throws IOException {
        parent.close();
    }
    
    @Override
    public synchronized void mark(int readlimit) {
        parent.mark(readlimit);
    }
    
    @Override
    public synchronized void reset() throws IOException {
        parent.reset();
    }
    
    @Override
    public boolean markSupported() {
        return parent.markSupported();
    }
    
    @Override
    public long transferTo(OutputStream out) throws IOException {
        return parent.transferTo(out);
    }
}
