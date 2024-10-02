package com.obify.hy.ims.service;

import com.obify.hy.ims.dto.square.OverviewRequestDTO;
import com.obify.hy.ims.dto.square.OverviewResponseDTO;

public interface FiInventoryService {
   OverviewResponseDTO inventoryOverview(OverviewRequestDTO requestDTO);
}
