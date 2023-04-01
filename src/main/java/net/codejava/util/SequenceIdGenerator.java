package net.codejava.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SequenceIdGenerator extends SequenceStyleGenerator {

    public static final String INCREMENT_PARAM = "1";

    public static final String DATE_FORMAT_DEFAULT = "yyMM";

    public static final String NUMBER_FORMAT_DEFAULT = "%06d";


    private String format;

    @Override
    public Serializable generate(SharedSessionContractImplementor session,
                                 Object object) throws HibernateException {


        LocalDate localDate = LocalDate.now();
        String formattedDate = localDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT_DEFAULT));
        String formattedNumber = String.format(NUMBER_FORMAT_DEFAULT, super.generate(session, object));
        return Long.parseLong(formattedDate.concat(formattedNumber));

    }
}

