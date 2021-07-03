package com.cyrillo.ativo.core.dataprovider;

import java.util.UUID;

public interface DataProviderInterface {
    public LoggingInterface getLoggingInterface();
    public AtivoRepositorioInterface getAtivoRepositorio();
    public UUID getUniqueKey();
}
