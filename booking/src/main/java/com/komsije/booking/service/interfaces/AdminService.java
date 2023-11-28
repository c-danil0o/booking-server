package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.model.Account;
import com.komsije.booking.service.interfaces.crud.CrudService;

public interface AdminService extends CrudService<AccountDto, Long> {
}
