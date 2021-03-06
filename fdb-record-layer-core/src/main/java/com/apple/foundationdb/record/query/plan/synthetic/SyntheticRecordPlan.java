/*
 * SyntheticRecordPlan.java
 *
 * This source file is part of the FoundationDB open source project
 *
 * Copyright 2015-2018 Apple Inc. and the FoundationDB project authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apple.foundationdb.record.query.plan.synthetic;

import com.apple.foundationdb.annotation.API;
import com.apple.foundationdb.record.ExecuteProperties;
import com.apple.foundationdb.record.PlanHashable;
import com.apple.foundationdb.record.RecordCursor;
import com.apple.foundationdb.record.provider.foundationdb.FDBRecordStore;
import com.apple.foundationdb.record.provider.foundationdb.FDBSyntheticRecord;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A plan for generating synthetic records without an explicit starting point.
 *
 * This kind of plan is similar to {@link com.apple.foundationdb.record.query.plan.plans.RecordQueryPlan}, in that
 * it takes a store, continuation, and execute properties and produces a cursor of records. However, it is <em>not</em>
 * run as part of ordinary query execution. In other words, while these plans in some sense represent
 * <code>SELECT * FROM synth_type</code>, an <em>actual</em> query to get the same underlying records would be
 * <code>SELECT * FROM t1 JOIN t2 USING k</code>.
 *
 */
@API(API.Status.INTERNAL)
public interface SyntheticRecordPlan extends PlanHashable  {
    /**
     * Execute this plan.
     * @param store record store against which to execute
     * @param continuation continuation from a previous execution of this same plan
     * @param executeProperties limits on execution
     * @return a cursor of synthetic records
     */
    @Nonnull
    RecordCursor<FDBSyntheticRecord> execute(@Nonnull FDBRecordStore store,
                                             @Nullable byte[] continuation,
                                             @Nonnull ExecuteProperties executeProperties);

    /**
     * Execute this plan.
     * @param store record store against which to execute
     * @return a cursor of synthetic records
     */
    @Nonnull
    default RecordCursor<FDBSyntheticRecord> execute(@Nonnull FDBRecordStore store) {
        return execute(store, null, ExecuteProperties.SERIAL_EXECUTE);
    }

}
