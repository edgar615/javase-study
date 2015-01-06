![image](img/ByteBuf类图.png)

*ByteBuf的层级关系*
    
    ├── ByteBuf
    │   ├── EmptyByteBuf
    │   ├── SwappedByteBuf
    │   ├── WrappedByteBuf
    │   │   ├── AdvancedLeakAwareByteBuf
    │   │   ├── UnreleasableByteBuf
    │   │   ├── SimpleLeakAwareByteBuf
    │   ├── AbstractByteBuf
    │   │   ├── AbstractDerivedByteBuf
    │   │   │   ├── ReadOnlyByteBuf
    │   │   │   ├── DuplicatedByteBuf
    │   │   │   ├── SlicedByteBuf
    │   │   ├── AbstractReferenceCountedByteBuf
    │   │   │   ├── ReadOnlyByteBufferBuf
    │   │   │   │   ├── ReadOnlyUnsafeDirectByteBuf
    │   │   │   ├── FixedCompositeByteBuf
    │   │   │   ├── UnpooledDirectByteBuf
    │   │   │   ├── UnpooledHeapByteBuf
    │   │   │   ├── CompositeByteBuf
    │   │   │   ├── UnpooledUnsafeDirectByteBuf
    │   │   │   ├── PooledByteBuf
    │   │   │   │   ├── PooledUnsafeDirectByteBuf
    │   │   │   │   ├── PooledDirectByteBuf
    │   │   │   │   ├── PooledHeapByteBuf
    
zzz