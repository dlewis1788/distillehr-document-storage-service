package com.projectivesoftware.distillehr.utility.storage;

import com.projectivesoftware.distillehr.domain.DocumentType;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

public class DocumentNameFormatter {

    public static String format(DocumentType documentType, List<String> documentProperties) throws ParseException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(documentType.name());

        if (documentProperties.size() > 0) {
            stringBuilder.append("_");
        } else {
            throw new ParseException("documentProperties must be not-empty.", documentType.name().length());
        }

        Iterator iterator = documentProperties.iterator();

        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next());

            if (!iterator.hasNext()) {
                break;
            } else {
                stringBuilder.append("_");
            }
        }

        stringBuilder.append(".pdf");

        return stringBuilder.toString();
    }
}