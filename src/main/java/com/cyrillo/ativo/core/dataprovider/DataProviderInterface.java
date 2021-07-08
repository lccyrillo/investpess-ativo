package com.cyrillo.ativo.core.dataprovider;

import java.util.UUID;

public interface DataProviderInterface {
    public LogInterface getLoggingInterface();
    public AtivoRepositorioInterface getAtivoRepositorio();
    public UUID getUniqueKey();
}