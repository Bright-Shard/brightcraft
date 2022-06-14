package dev.brightshard.brightcraft.events;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.brightshard.brightcraft.Main.LOGGER;

public class EventData<DataType, CIRType> {
    private DataType data;
    private CallbackInfoReturnable<CIRType> cir;
    private CallbackInfo ci;

    public EventData(DataType data, CallbackInfoReturnable<CIRType> cir) {
        this.data = data;
        this.cir = cir;
    }
    public EventData(DataType data, CallbackInfo ci) {
        this.data = data;
        this.ci = ci;
    }
    public EventData(CallbackInfoReturnable<CIRType> cir) {
        this.cir = cir;
    }
    public EventData(CallbackInfo ci) {
        this.ci = ci;
    }
    public EventData(DataType data) {
        this.data = data;
    }

    public <Type> Type getData() {
        try {
            @SuppressWarnings("unchecked")
            Type returnData = (Type) this.data;
            return returnData;
        } catch (ClassCastException e) {
            LOGGER.error("Error converting event data type!" + this.data + this.ci + this.cir + e);
            throw new RuntimeException("Error converting event data type: "+e);
        }
    }
    public <Type> CallbackInfoReturnable<Type> getCIR() {
        try {
            @SuppressWarnings("unchecked")
            CallbackInfoReturnable<Type> returnData = (CallbackInfoReturnable<Type>) this.cir;
            return returnData;
        } catch (ClassCastException e) {
            LOGGER.error("Error converting event CIR type!" + this.data + this.ci + this.cir + e);
            throw new RuntimeException("Error converting event CIR type: "+e);
        }
    }
    public CallbackInfo getCI() {
        return this.ci;
    }
}
