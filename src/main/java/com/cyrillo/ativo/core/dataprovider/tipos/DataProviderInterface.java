package com.cyrillo.ativo.core.dataprovider.tipos;

import java.util.UUID;

public interface DataProviderInterface {
    public LoggingInterface getLoggingInterface();
    public AtivoRepositorioInterface getAtivoRepositorio();
    public UUID getUniqueKey();
}
