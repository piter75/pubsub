/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
<<<<<<< HEAD:mapped-api/clients/src/main/java/com/google/kafka/clients/consumer/PubsubConsumer.java
package com.google.kafka.cients.consumer;
=======

package com.google.pubsub.clients.consumer;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.kafka.clients.ClientUtils;
import org.apache.kafka.clients.Metadata;
import org.apache.kafka.clients.NetworkClient;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.ConfigException;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.metrics.JmxReporter;
import org.apache.kafka.common.metrics.MetricConfig;
import org.apache.kafka.common.metrics.Metrics;
import org.apache.kafka.common.metrics.MetricsReporter;
import org.apache.kafka.common.network.ChannelBuilder;
import org.apache.kafka.common.network.Selector;
import org.apache.kafka.common.requests.MetadataRequest;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.utils.AppInfoParser;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class PubsubConsumer<K, V> implements Consumer<K, V> {

  public PubsubConsumer(Map<String, Object> configs) {

  }

  public PubsubConsumer(Map<String, Object> configs,
      Deserializer<K> keyDeserializer,
      Deserializer<V> valueDeserializer) {

  }

  public PubsubConsumer(Properties properties) {

  }

  public PubsubConsumer(Properties properties,
      Deserializer<K> keyDeserializer,
      Deserializer<V> valueDeserializer) {

  }

  /**
   * Get the set of partitions currently assigned to this consumer. If subscription happened by directly assigning
   * partitions using {@link #assign(Collection)} then this will simply return the same partitions that
   * were assigned. If topic subscription was used, then this will give the set of topic partitions currently assigned
   * to the consumer (which may be none if the assignment hasn't happened yet, or the partitions are in the
   * process of getting reassigned).
   * @return The set of partitions currently assigned to this consumer
   */
  public Set<TopicPartition> assignment() {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Get the current subscription. Will return the same topics used in the most recent call to
   * {@link #subscribe(Collection, ConsumerRebalanceListener)}, or an empty set if no such call has been made.
   * @return The set of topics currently subscribed to
   */
  public Set<String> subscription() {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Subscribe to the given list of topics to get dynamically
   * assigned partitions. <b>Topic subscriptions are not incremental. This list will replace the current
   * assignment (if there is one).</b> Note that it is not possible to combine topic subscription with group management
   * with manual partition assignment through {@link #assign(Collection)}.
   *
   * If the given list of topics is empty, it is treated the same as {@link #unsubscribe()}.
   *
   * <p>
   * As part of group management, the consumer will keep track of the list of consumers that belong to a particular
   * group and will trigger a rebalance operation if one of the following events trigger -
   * <ul>
   * <li>Number of partitions change for any of the subscribed list of topics
   * <li>Topic is created or deleted
   * <li>An existing member of the consumer group dies
   * <li>A new member is added to an existing consumer group via the join API
   * </ul>
   * <p>
   * When any of these events are triggered, the provided listener will be invoked first to indicate that
   * the consumer's assignment has been revoked, and then again when the new assignment has been received.
   * Note that this listener will immediately override any listener set in a previous call to subscribe.
   * It is guaranteed, however, that the partitions revoked/assigned through this interface are from topics
   * subscribed in this call. See {@link ConsumerRebalanceListener} for more details.
   *
   * @param topics The list of topics to subscribe to
   * @param listener Non-null listener instance to get notifications on partition assignment/revocation for the
   *                 subscribed topics
   * @throws IllegalArgumentException If topics is null or contains null or empty elements
   */
  public void subscribe(Collection<String> topics, ConsumerRebalanceListener listener) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Subscribe to the given list of topics to get dynamically assigned partitions.
   * <b>Topic subscriptions are not incremental. This list will replace the current
   * assignment (if there is one).</b> It is not possible to combine topic subscription with group management
   * with manual partition assignment through {@link #assign(Collection)}.
   *
   * If the given list of topics is empty, it is treated the same as {@link #unsubscribe()}.
   *
   * <p>
   * This is a short-hand for {@link #subscribe(Collection, ConsumerRebalanceListener)}, which
   * uses a noop listener. If you need the ability to seek to particular offsets, you should prefer
   * {@link #subscribe(Collection, ConsumerRebalanceListener)}, since group rebalances will cause partition offsets
   * to be reset. You should also provide your own listener if you are doing your own offset
   * management since the listener gives you an opportunity to commit offsets before a rebalance finishes.
   *
   * @param topics The list of topics to subscribe to
   * @throws IllegalArgumentException If topics is null or contains null or empty elements
   */
  @Override
  public void subscribe(Collection<String> topics) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Subscribe to all topics matching specified pattern to get dynamically assigned partitions. The pattern matching will be done periodically against topics
   * existing at the time of check.
   *
   * <p>
   * As part of group management, the consumer will keep track of the list of consumers that
   * belong to a particular group and will trigger a rebalance operation if one of the
   * following events trigger -
   * <ul>
   * <li>Number of partitions change for any of the subscribed list of topics
   * <li>Topic is created or deleted
   * <li>An existing member of the consumer group dies
   * <li>A new member is added to an existing consumer group via the join API
   * </ul>
   *
   * @param pattern Pattern to subscribe to
   * @param listener Non-null listener instance to get notifications on partition assignment/revocation for the
   *                 subscribed topics
   * @throws IllegalArgumentException If pattern is null
   */
  @Override
  public void subscribe(Pattern pattern, ConsumerRebalanceListener listener) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Unsubscribe from topics currently subscribed with {@link #subscribe(Collection)}. This
   * also clears any partitions directly assigned through {@link #assign(Collection)}.
   */
  public void unsubscribe() {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Manually assign a list of partitions to this consumer. This interface does not allow for incremental assignment
   * and will replace the previous assignment (if there is one).
   *
   * If the given list of topic partitions is empty, it is treated the same as {@link #unsubscribe()}.
   *
   * <p>
   * Manual topic assignment through this method does not use the consumer's group management
   * functionality. As such, there will be no rebalance operation triggered when group membership or cluster and topic
   * metadata change. Note that it is not possible to use both manual partition assignment with {@link #assign(Collection)}
   * and group assignment with {@link #subscribe(Collection, ConsumerRebalanceListener)}.
   *
   * @param partitions The list of partitions to assign this consumer
   * @throws IllegalArgumentException If partitions is null or contains null or empty topics
   */
  @Override
  public void assign(Collection<TopicPartition> partitions) {
    throw new NotImplementedException("Not yet implemented");
  }


  /**
   * Fetch data for the topics or partitions specified using one of the subscribe/assign APIs. It is an error to not have
   * subscribed to any topics or partitions before polling for data.
   * <p>
   * On each poll, consumer will try to use the last consumed offset as the starting offset and fetch sequentially. The last
   * consumed offset can be manually set through {@link #seek(TopicPartition, long)} or automatically set as the last committed
   * offset for the subscribed list of partitions
   *
   *
   * @param timeout The time, in milliseconds, spent waiting in poll if data is not available in the buffer.
   *            If 0, returns immediately with any records that are available currently in the buffer, else returns empty.
   *            Must not be negative.
   * @return map of topic to records since the last fetch for the subscribed list of topics and partitions
   *
   * @throws org.apache.kafka.clients.consumer.InvalidOffsetException if the offset for a partition or set of
   *             partitions is undefined or out of range and no offset reset policy has been configured
   * @throws org.apache.kafka.common.errors.WakeupException if {@link #wakeup()} is called before or while this
   *             function is called
   * @throws org.apache.kafka.common.errors.InterruptException if the calling thread is interrupted before or while
   *             this function is called
   * @throws org.apache.kafka.common.errors.AuthorizationException if caller lacks Read access to any of the subscribed
   *             topics or to the configured groupId
   * @throws org.apache.kafka.common.KafkaException for any other unrecoverable errors (e.g. invalid groupId or
   *             session timeout, errors deserializing key/value pairs, or any new error cases in future versions)
   * @throws java.lang.IllegalArgumentException if the timeout value is negative
   * @throws java.lang.IllegalStateException if the consumer is not subscribed to any topics or manually assigned any
   *             partitions to consume from
   */
  @Override
  public ConsumerRecords<K, V> poll(long timeout) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Commit offsets returned on the last {@link #poll(long) poll()} for all the subscribed list of topics and partitions.
   * <p>
   * This commits offsets only to Kafka. The offsets committed using this API will be used on the first fetch after
   * every rebalance and also on startup. As such, if you need to store offsets in anything other than Kafka, this API
   * should not be used.
   * <p>
   * This is a synchronous commits and will block until either the commit succeeds or an unrecoverable error is
   * encountered (in which case it is thrown to the caller).
   *
   * @throws org.apache.kafka.clients.consumer.CommitFailedException if the commit failed and cannot be retried.
   *             This can only occur if you are using automatic group management with {@link #subscribe(Collection)},
   *             or if there is an active group with the same groupId which is using group management.
   * @throws org.apache.kafka.common.errors.WakeupException if {@link #wakeup()} is called before or while this
   *             function is called
   * @throws org.apache.kafka.common.errors.InterruptException if the calling thread is interrupted before or while
   *             this function is called
   * @throws org.apache.kafka.common.errors.AuthorizationException if not authorized to the topic or to the
   *             configured groupId
   * @throws org.apache.kafka.common.KafkaException for any other unrecoverable errors (e.g. if offset metadata
   *             is too large or if the committed offset is invalid).
   */
  @Override
  public void commitSync() {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Commit the specified offsets for the specified list of topics and partitions.
   * <p>
   * This commits offsets to Kafka. The offsets committed using this API will be used on the first fetch after every
   * rebalance and also on startup. As such, if you need to store offsets in anything other than Kafka, this API
   * should not be used. The committed offset should be the next message your application will consume,
   * i.e. lastProcessedMessageOffset + 1.
   * <p>
   * This is a synchronous commits and will block until either the commit succeeds or an unrecoverable error is
   * encountered (in which case it is thrown to the caller).
   *
   * @param offsets A map of offsets by partition with associated metadata
   * @throws org.apache.kafka.clients.consumer.CommitFailedException if the commit failed and cannot be retried.
   *             This can only occur if you are using automatic group management with {@link #subscribe(Collection)},
   *             or if there is an active group with the same groupId which is using group management.
   * @throws org.apache.kafka.common.errors.WakeupException if {@link #wakeup()} is called before or while this
   *             function is called
   * @throws org.apache.kafka.common.errors.InterruptException if the calling thread is interrupted before or while
   *             this function is called
   * @throws org.apache.kafka.common.errors.AuthorizationException if not authorized to the topic or to the
   *             configured groupId
   * @throws org.apache.kafka.common.KafkaException for any other unrecoverable errors (e.g. if offset metadata
   *             is too large or if the committed offset is invalid).
   */
  @Override
  public void commitSync(final Map<TopicPartition, OffsetAndMetadata> offsets) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Commit offsets returned on the last {@link #poll(long) poll()} for all the subscribed list of topics and partition.
   * Same as {@link #commitAsync(OffsetCommitCallback) commitAsync(null)}
   */
  @Override
  public void commitAsync() {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Commit offsets returned on the last {@link #poll(long) poll()} for the subscribed list of topics and partitions.
   * <p>
   * This commits offsets only to Kafka. The offsets committed using this API will be used on the first fetch after
   * every rebalance and also on startup. As such, if you need to store offsets in anything other than Kafka, this API
   * should not be used.
   * <p>
   * This is an asynchronous call and will not block. Any errors encountered are either passed to the callback
   * (if provided) or discarded.
   *
   * @param callback Callback to invoke when the commit completes
   */
  @Override
  public void commitAsync(OffsetCommitCallback callback) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Commit the specified offsets for the specified list of topics and partitions to Kafka.
   * <p>
   * This commits offsets to Kafka. The offsets committed using this API will be used on the first fetch after every
   * rebalance and also on startup. As such, if you need to store offsets in anything other than Kafka, this API
   * should not be used. The committed offset should be the next message your application will consume,
   * i.e. lastProcessedMessageOffset + 1.
   * <p>
   * This is an asynchronous call and will not block. Any errors encountered are either passed to the callback
   * (if provided) or discarded.
   *
   * @param offsets A map of offsets by partition with associate metadata. This map will be copied internally, so it
   *                is safe to mutate the map after returning.
   * @param callback Callback to invoke when the commit completes
   */
  @Override
  public void commitAsync(final Map<TopicPartition, OffsetAndMetadata> offsets,
      OffsetCommitCallback callback) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Overrides the fetch offsets that the consumer will use on the next {@link #poll(long) poll(timeout)}. If this API
   * is invoked for the same partition more than once, the latest offset will be used on the next poll(). Note that
   * you may lose data if this API is arbitrarily used in the middle of consumption, to reset the fetch offsets
   */
  @Override
  public void seek(TopicPartition partition, long offset) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Seek to the first offset for each of the given partitions. This function evaluates lazily, seeking to the
   * first offset in all partitions only when {@link #poll(long)} or {@link #position(TopicPartition)} are called.
   * If no partition is provided, seek to the first offset for all of the currently assigned partitions.
   */
  public void seekToBeginning(Collection<TopicPartition> partitions) {

  }

  /**
   * Seek to the last offset for each of the given partitions. This function evaluates lazily, seeking to the
   * final offset in all partitions only when {@link #poll(long)} or {@link #position(TopicPartition)} are called.
   * If no partition is provided, seek to the final offset for all of the currently assigned partitions.
   */
  public void seekToEnd(Collection<TopicPartition> partitions) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Get the offset of the <i>next record</i> that will be fetched (if a record with that offset exists).
   *
   * @param partition The partition to get the position for
   * @return The offset
   * @throws org.apache.kafka.clients.consumer.InvalidOffsetException if no offset is currently defined for
   *             the partition
   * @throws org.apache.kafka.common.errors.WakeupException if {@link #wakeup()} is called before or while this
   *             function is called
   * @throws org.apache.kafka.common.errors.InterruptException if the calling thread is interrupted before or while
   *             this function is called
   * @throws org.apache.kafka.common.errors.AuthorizationException if not authorized to the topic or to the
   *             configured groupId
   * @throws org.apache.kafka.common.KafkaException for any other unrecoverable errors
   */
  public long position(TopicPartition partition) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Get the last committed offset for the given partition (whether the commit happened by this process or
   * another). This offset will be used as the position for the consumer in the event of a failure.
   * <p>
   * This call may block to do a remote call if the partition in question isn't assigned to this consumer or if the
   * consumer hasn't yet initialized its cache of committed offsets.
   *
   * @param partition The partition to check
   * @return The last committed offset and metadata or null if there was no prior commit
   * @throws org.apache.kafka.common.errors.WakeupException if {@link #wakeup()} is called before or while this
   *             function is called
   * @throws org.apache.kafka.common.errors.InterruptException if the calling thread is interrupted before or while
   *             this function is called
   * @throws org.apache.kafka.common.errors.AuthorizationException if not authorized to the topic or to the
   *             configured groupId
   * @throws org.apache.kafka.common.KafkaException for any other unrecoverable errors
   */
  public OffsetAndMetadata committed(TopicPartition partition) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Get the metrics kept by the consumer
   */
  public Map<MetricName, ? extends Metric> metrics() {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Get metadata about the partitions for a given topic. This method will issue a remote call to the server if it
   * does not already have any metadata about the given topic.
   *
   * @param topic The topic to get partition metadata for
   * @return The list of partitions
   * @throws org.apache.kafka.common.errors.WakeupException if {@link #wakeup()} is called before or while this
   *             function is called
   * @throws org.apache.kafka.common.errors.InterruptException if the calling thread is interrupted before or while
   *             this function is called
   * @throws org.apache.kafka.common.errors.AuthorizationException if not authorized to the specified topic
   * @throws org.apache.kafka.common.errors.TimeoutException if the topic metadata could not be fetched before
   *             expiration of the configured request timeout
   * @throws org.apache.kafka.common.KafkaException for any other unrecoverable errors
   */
  public List<PartitionInfo> partitionsFor(String topic) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Get metadata about partitions for all topics that the user is authorized to view. This method will issue a
   * remote call to the server.
   * @return The map of topics and its partitions
   * @throws org.apache.kafka.common.errors.WakeupException if {@link #wakeup()} is called before or while this
   *             function is called
   * @throws org.apache.kafka.common.errors.InterruptException if the calling thread is interrupted before or while
   *             this function is called
   * @throws org.apache.kafka.common.errors.TimeoutException if the topic metadata could not be fetched before
   *             expiration of the configured request timeout
   * @throws org.apache.kafka.common.KafkaException for any other unrecoverable errors
   */
  public Map<String, List<PartitionInfo>> listTopics() {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Suspend fetching from the requested partitions. Future calls to {@link #poll(long)} will not return
   * any records from these partitions until they have been resumed using {@link #resume(Collection)}.
   * Note that this method does not affect partition subscription. In particular, it does not cause a group
   * rebalance when automatic assignment is used.
   * @param partitions The partitions which should be paused
   */
  public void pause(Collection<TopicPartition> partitions) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Resume specified partitions which have been paused with {@link #pause(Collection)}. New calls to
   * {@link #poll(long)} will return records from these partitions if there are any to be fetched.
   * If the partitions were not previously paused, this method is a no-op.
   * @param partitions The partitions which should be resumed
   */
  public void resume(Collection<TopicPartition> partitions) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Get the set of partitions that were previously paused by a call to {@link #pause(Collection)}.
   *
   * @return The set of paused partitions
   */
  public Set<TopicPartition> paused() {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Look up the offsets for the given partitions by timestamp. The returned offset for each partition is the
   * earliest offset whose timestamp is greater than or equal to the given timestamp in the corresponding partition.
   *
   * This is a blocking call. The consumer does not have to be assigned the partitions.
   * If the message format version in a partition is before 0.10.0, i.e. the messages do not have timestamps, null
   * will be returned for that partition.
   *
   * Notice that this method may block indefinitely if the partition does not exist.
   *
   * @param timestampsToSearch the mapping from partition to the timestamp to look up.
   * @return a mapping from partition to the timestamp and offset of the first message with timestamp greater
   *         than or equal to the target timestamp. {@code null} will be returned for the partition if there is no
   *         such message.
   * @throws IllegalArgumentException if the target timestamp is negative.
   * @throws org.apache.kafka.common.errors.UnsupportedVersionException if the broker does not support looking up
   *         the offsets by timestamp.
   */
  public Map<TopicPartition, OffsetAndTimestamp> offsetsForTimes(Map<TopicPartition, Long> timestampsToSearch) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Get the first offset for the given partitions.
   * <p>
   * Notice that this method may block indefinitely if the partition does not exist.
   * This method does not change the current consumer position of the partitions.
   *
   * @see #seekToBeginning(Collection)
   *
   * @param partitions the partitions to get the earliest offsets.
   * @return The earliest available offsets for the given partitions
   */
  public Map<TopicPartition, Long> beginningOffsets(Collection<TopicPartition> partitions) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Get the last offset for the given partitions. The last offset of a partition is the offset of the upcoming
   * message, i.e. the offset of the last available message + 1.
   * <p>
   * Notice that this method may block indefinitely if the partition does not exist.
   * This method does not change the current consumer position of the partitions.
   *
   * @see #seekToEnd(Collection)
   *
   * @param partitions the partitions to get the end offsets.
   * @return The end offsets for the given partitions.
   */
  public Map<TopicPartition, Long> endOffsets(Collection<TopicPartition> partitions) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Close the consumer, waiting for up to the default timeout of 30 seconds for any needed cleanup.
   * If auto-commit is enabled, this will commit the current offsets if possible within the default
   * timeout. See {@link #close(long, TimeUnit)} for details. Note that {@link #wakeup()}
   * cannot be used to interrupt close.
   *
   * @throws org.apache.kafka.common.errors.InterruptException if the calling thread is interrupted
   * before or while this function is called
   */
  public void close() {

  }

  /**
   * Tries to close the consumer cleanly within the specified timeout. This method waits up to
   * <code>timeout</code> for the consumer to complete pending commits and leave the group.
   * If auto-commit is enabled, this will commit the current offsets if possible within the
   * timeout. If the consumer is unable to complete offset commits and gracefully leave the group
   * before the timeout expires, the consumer is force closed. Note that {@link #wakeup()} cannot be
   * used to interrupt close.
   *
   * @param timeout The maximum time to wait for consumer to close gracefully. The value must be
   *                non-negative. Specifying a timeout of zero means do not wait for pending requests to complete.
   * @param timeUnit The time unit for the <code>timeout</code>
   * @throws InterruptException If the thread is interrupted before or while this function is called
   * @throws IllegalArgumentException If the <code>timeout</code> is negative.
   */
  public void close(long timeout, TimeUnit timeUnit) {
    throw new NotImplementedException("Not yet implemented");
  }

  /**
   * Wakeup the consumer. This method is thread-safe and is useful in particular to abort a long poll.
   * The thread which is blocking in an operation will throw {@link org.apache.kafka.common.errors.WakeupException}.
   * If no thread is blocking in a method which can throw {@link org.apache.kafka.common.errors.WakeupException}, the next call to such a method will raise it instead.
   */
  public void wakeup() {
    throw new NotImplementedException("Not yet implemented");
  }
}